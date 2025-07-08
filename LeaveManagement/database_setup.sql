-- Script để setup dữ liệu cho bảng Features
-- Chạy script này trong SQL Server Management Studio hoặc tool quản lý database

-- Thêm các Features (chức năng) vào bảng Features
INSERT INTO Features (feature_name, EntryPoint) VALUES 
('Create Leave Request', '/leave/create'),
('View Leave Request', '/leave/view'),
('Update Leave Request', '/leave/update'),
('Review Leave Request', '/leave/review'),
('Leave Request Agenda', '/leave/agenda');

-- Thêm Roles (vai trò) vào bảng Roles (nếu chưa có)
INSERT INTO Roles (role_name) VALUES 
('Employee'),
('Manager'),
('HR'),
('Admin');

-- Thêm Role-Feature mappings (phân quyền)
-- Employee có thể tạo, xem, cập nhật leave request
INSERT INTO Role_features (rid, fid) 
SELECT r.rid, f.fid 
FROM Roles r, Features f 
WHERE r.role_name = 'Employee' 
AND f.feature_name IN ('Create Leave Request', 'View Leave Request', 'Update Leave Request');

-- Manager có thể review leave request
INSERT INTO Role_features (rid, fid) 
SELECT r.rid, f.fid 
FROM Roles r, Features f 
WHERE r.role_name = 'Manager' 
AND f.feature_name IN ('Review Leave Request', 'Leave Request Agenda');

-- HR có thể làm tất cả
INSERT INTO Role_features (rid, fid) 
SELECT r.rid, f.fid 
FROM Roles r, Features f 
WHERE r.role_name = 'HR';

-- Admin có thể làm tất cả
INSERT INTO Role_features (rid, fid) 
SELECT r.rid, f.fid 
FROM Roles r, Features f 
WHERE r.role_name = 'Admin';

-- Kiểm tra dữ liệu đã được thêm
SELECT 'Features:' as TableName;
SELECT * FROM Features;

SELECT 'Roles:' as TableName;
SELECT * FROM Roles;

SELECT 'Role_Features:' as TableName;
SELECT r.role_name, f.feature_name, f.EntryPoint
FROM Role_features rf
JOIN Roles r ON rf.rid = r.rid
JOIN Features f ON rf.fid = f.fid
ORDER BY r.role_name, f.feature_name; 