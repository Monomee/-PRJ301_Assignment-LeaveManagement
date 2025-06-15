USE HR_Management_DB
-- 1. Thêm dữ liệu cho bảng Departments
INSERT INTO Departments (department_name) VALUES 
(N'IT'),
(N'HR');

-- 2. Thêm dữ liệu cho bảng Users
INSERT INTO Users (user_name, password, full_name, email, did, manager_id, created_at, updated_at) VALUES 
('teo', 'bcrypt_hash_password_1', N'Mr Tèo', 'teo@example.com', 1, 2, GETDATE(), GETDATE()), -- Nhân viên, thuộc phòng IT, quản lý là Mr Tít
('tit', 'bcrypt_hash_password_2', N'Mr Tít', 'tit@example.com', 1, 3, GETDATE(), GETDATE()), -- Quản lý, thuộc phòng IT, quản lý là Mr A
('user_a', 'bcrypt_hash_password_3', N'Mr A', 'a@example.com', 1, NULL, GETDATE(), GETDATE()), -- Trưởng phòng IT, không có quản lý
('user_b', 'bcrypt_hash_password_4', N'Mr B', 'b@example.com', 1, 2, GETDATE(), GETDATE()), -- Nhân viên, thuộc phòng IT, quản lý là Mr Tít
('hr_user', 'bcrypt_hash_password_5', N'Ms HR', 'hr@example.com', 2, NULL, GETDATE(), GETDATE()); -- Trưởng phòng HR

-- 3. Thêm dữ liệu cho bảng Roles
INSERT INTO Roles (role_name) VALUES 
(N'Nhân viên'),
(N'Quản lý'),
(N'Trưởng phòng');

-- 4. Thêm dữ liệu cho bảng User_roles
INSERT INTO User_roles (uid, rid) VALUES 
(1, 1), -- Mr Tèo: Nhân viên
(2, 2), -- Mr Tít: Quản lý
(3, 3), -- Mr A: Trưởng phòng
(4, 1), -- Mr B: Nhân viên
(5, 3); -- Ms HR: Trưởng phòng

-- 5. Thêm dữ liệu cho bảng Features
INSERT INTO Features (feature_name) VALUES 
(N'Tạo đơn xin nghỉ phép'),
(N'Xem danh sách đơn'),
(N'Duyệt đơn xin nghỉ phép'),
(N'Xem Agenda');

-- 6. Thêm dữ liệu cho bảng Role_features
INSERT INTO Role_features (rid, fid) VALUES 
(1, 1), -- Nhân viên: Tạo đơn
(1, 2), -- Nhân viên: Xem danh sách đơn (của chính mình)
(2, 2), -- Quản lý: Xem danh sách đơn (của mình và cấp dưới)
(2, 3), -- Quản lý: Duyệt đơn
(3, 2), -- Trưởng phòng: Xem danh sách đơn
(3, 3), -- Trưởng phòng: Duyệt đơn
(3, 4); -- Trưởng phòng: Xem Agenda

-- 7. Thêm dữ liệu cho bảng Leave_requests
INSERT INTO Leave_requests (uid, title, from_date, to_date, reason, status, processed_by, processed_reason, created_at, updated_at) VALUES 
(1, N'Xin nghỉ cưới', '2025-01-01', '2025-01-03', N'Em nghỉ lấy vợ', 'inprogress', NULL, NULL, GETDATE(), GETDATE()), -- Mr Tèo, đang chờ duyệt
(1, N'Xin nghỉ ốm', '2025-02-01', '2025-02-02', N'Ốm nặng', 'approved', 2, N'Đồng ý cho nghỉ', GETDATE(), GETDATE()), -- Mr Tèo, được Mr Tít duyệt
(4, N'Xin nghỉ du lịch', '2025-01-01', '2025-01-05', N'Đi chơi với gia đình', 'rejected', 2, N'Không đủ lý do', GETDATE(), GETDATE()), -- Mr B, bị Mr Tít từ chối
(2, N'Xin nghỉ hội thảo', '2025-03-01', '2025-03-02', N'Tham gia hội thảo công nghệ', 'inprogress', NULL, NULL, GETDATE(), GETDATE()); -- Mr Tít, đang chờ duyệt