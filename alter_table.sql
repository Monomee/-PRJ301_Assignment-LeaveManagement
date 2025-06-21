USE HR_Management_DB;

-- 1. Kiểm tra và thêm cột EntryPoint nếu chưa tồn tại
IF NOT EXISTS (SELECT 1 FROM sys.columns WHERE Name = N'entrypoint' AND Object_ID = Object_ID(N'Features'))
BEGIN
    ALTER TABLE Features
    ADD EntryPoint VARCHAR(255) NOT NULL DEFAULT '';
END;

-- 2. Xóa dữ liệu cũ trong Role_features và Features
DELETE FROM Role_features;
DELETE FROM Features;

-- 3. Thêm dữ liệu mới cho bảng Features
INSERT INTO Features (feature_name, EntryPoint) VALUES 
(N'Tạo đơn', '/leave/create'),
(N'Xóa, sửa đơn', '/leave/update'),
(N'Duyệt đơn', '/leave/review'),
(N'Xem đơn', '/leave/view'),
(N'Xem Agenda', '/leave/agenda'),
(N'Xem danh sách đơn', '/leave/list');

-- 4. Thêm dữ liệu mới cho bảng Role_features
INSERT INTO Role_features (rid, fid) VALUES 
(1, (SELECT fid FROM Features WHERE EntryPoint = '/leave/create')), -- Nhân viên: Tạo đơn
(1, (SELECT fid FROM Features WHERE EntryPoint = '/leave/update')), -- Nhân viên: Xóa, sửa đơn
(1, (SELECT fid FROM Features WHERE EntryPoint = '/leave/view')),   -- Nhân viên: Xem đơn
(2, (SELECT fid FROM Features WHERE EntryPoint = '/leave/update')), -- Quản lý: Xóa, sửa đơn
(2, (SELECT fid FROM Features WHERE EntryPoint = '/leave/review')), -- Quản lý: Duyệt đơn
(2, (SELECT fid FROM Features WHERE EntryPoint = '/leave/view')),   -- Quản lý: Xem đơn
(2, (SELECT fid FROM Features WHERE EntryPoint = '/leave/list')),   -- Quản lý: Xem danh sách đơn
(3, (SELECT fid FROM Features WHERE EntryPoint = '/leave/update')), -- Trưởng phòng: Xóa, sửa đơn
(3, (SELECT fid FROM Features WHERE EntryPoint = '/leave/review')), -- Trưởng phòng: Duyệt đơn
(3, (SELECT fid FROM Features WHERE EntryPoint = '/leave/view')),   -- Trưởng phòng: Xem đơn
(3, (SELECT fid FROM Features WHERE EntryPoint = '/leave/agenda')), -- Trưởng phòng: Xem Agenda
(3, (SELECT fid FROM Features WHERE EntryPoint = '/leave/list'));   -- Trưởng phòng: Xem danh sách đơn