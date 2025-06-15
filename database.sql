USE HR_Management_DB

-- Tạo bảng Departments
CREATE TABLE Departments (
    did INT IDENTITY(1,1) PRIMARY KEY,
    department_name NVARCHAR(100) NOT NULL,
    CONSTRAINT UQ_department_name UNIQUE (department_name)
);
CREATE NONCLUSTERED INDEX idx_department_name ON Departments(department_name);

-- Tạo bảng Users
CREATE TABLE Users (
    uid INT IDENTITY(1,1) PRIMARY KEY,
    user_name VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    full_name NVARCHAR(100) NOT NULL,
    email VARCHAR(100),
    did INT NOT NULL,
    manager_id INT,
    created_at DATETIME2 DEFAULT GETDATE(),
    updated_at DATETIME2 DEFAULT GETDATE(),
    CONSTRAINT UQ_user_name UNIQUE (user_name),
    CONSTRAINT UQ_email UNIQUE (email),
    CONSTRAINT FK_users_dept FOREIGN KEY (did) REFERENCES Departments(did),
    CONSTRAINT FK_users_manager FOREIGN KEY (manager_id) REFERENCES Users(uid)
);
CREATE NONCLUSTERED INDEX idx_user_name ON Users(user_name);
CREATE NONCLUSTERED INDEX idx_email ON Users(email);
CREATE NONCLUSTERED INDEX idx_manager_id ON Users(manager_id);

-- Trigger để cập nhật updated_at trong Users
GO
CREATE TRIGGER trg_users_update
ON Users
AFTER UPDATE
AS
BEGIN
    UPDATE Users
    SET updated_at = GETDATE()
    FROM Users u
    INNER JOIN inserted i ON u.uid = i.uid;
END;
GO

-- Tạo bảng Roles
CREATE TABLE Roles (
    rid INT IDENTITY(1,1) PRIMARY KEY,
    role_name NVARCHAR(50) NOT NULL,
    CONSTRAINT UQ_role_name UNIQUE (role_name)
);
CREATE NONCLUSTERED INDEX idx_role_name ON Roles(role_name);

-- Tạo bảng User_roles
CREATE TABLE User_roles (
    uid INT NOT NULL,
    rid INT NOT NULL,
    CONSTRAINT PK_user_roles PRIMARY KEY (uid, rid),
    CONSTRAINT FK_user_roles_user FOREIGN KEY (uid) REFERENCES Users(uid),
    CONSTRAINT FK_user_roles_role FOREIGN KEY (rid) REFERENCES Roles(rid)
);
CREATE NONCLUSTERED INDEX idx_rid ON User_roles(rid);

-- Tạo bảng Features
CREATE TABLE Features (
    fid INT IDENTITY(1,1) PRIMARY KEY,
    feature_name NVARCHAR(50) NOT NULL,
    CONSTRAINT UQ_feature_name UNIQUE (feature_name)
);
CREATE NONCLUSTERED INDEX idx_feature_name ON Features(feature_name);

-- Tạo bảng Role_features
CREATE TABLE Role_features (
    rid INT NOT NULL,
    fid INT NOT NULL,
    CONSTRAINT PK_role_features PRIMARY KEY (rid, fid),
    CONSTRAINT FK_role_features_role FOREIGN KEY (rid) REFERENCES Roles(rid),
    CONSTRAINT FK_role_features_feature FOREIGN KEY (fid) REFERENCES Features(fid)
);
CREATE NONCLUSTERED INDEX idx_fid ON Role_features(fid);

-- Tạo bảng Leave_requests
CREATE TABLE Leave_requests (
    lid INT IDENTITY(1,1) PRIMARY KEY,
    uid INT NOT NULL,
    title NVARCHAR(100) NOT NULL,
    from_date DATE NOT NULL,
    to_date DATE NOT NULL,
    reason NVARCHAR(MAX) NOT NULL,
    status NVARCHAR(20) NOT NULL CHECK (status IN ('inprogress', 'approved', 'rejected')) DEFAULT 'inprogress',
    processed_by INT,
    processed_reason NVARCHAR(MAX),
    created_at DATETIME2 DEFAULT GETDATE(),
    updated_at DATETIME2 DEFAULT GETDATE(),
    CONSTRAINT FK_leave_requests_user FOREIGN KEY (uid) REFERENCES Users(uid),
    CONSTRAINT FK_leave_requests_processed_by FOREIGN KEY (processed_by) REFERENCES Users(uid),
    CONSTRAINT chk_dates CHECK (from_date <= to_date)
);
CREATE NONCLUSTERED INDEX idx_uid ON Leave_requests(uid);
CREATE NONCLUSTERED INDEX idx_status ON Leave_requests(status);
CREATE NONCLUSTERED INDEX idx_dates ON Leave_requests(from_date, to_date);

-- Trigger để cập nhật updated_at trong Leave_requests
GO
CREATE TRIGGER trg_leave_requests_update
ON Leave_requests
AFTER UPDATE
AS
BEGIN
    UPDATE Leave_requests
    SET updated_at = GETDATE()
    FROM Leave_requests lr
    INNER JOIN inserted i ON lr.lid = i.lid;
END;
GO