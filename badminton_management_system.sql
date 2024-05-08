-- 羽毛球场地管理系统数据库
DROP DATABASE IF EXISTS ymq;

CREATE DATABASE ymq CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE ymq;

-- 场地表
CREATE TABLE venue (
    venue_id INT PRIMARY KEY auto_increment, 
    name VARCHAR(255) NOT NULL, -- 名称
    address VARCHAR(255) NOT NULL, -- 地址
    area_count INT NOT NULL -- 区域数量
);

-- 裁判长表
CREATE TABLE chief_referee (
    referee_id INT PRIMARY KEY auto_increment, 
    phone VARCHAR(255) NOT NULL, -- 手机号
    name VARCHAR(255) NOT NULL, -- 姓名
    password VARCHAR(255) NOT NULL -- 密码
);

-- 活动表
CREATE TABLE event (
    event_id INT PRIMARY KEY auto_increment, 
    name VARCHAR(255) NOT NULL, -- 名称
    introduction VARCHAR(255), -- 活动介绍
    venue_id INT, -- 场地ID
    required_area_count INT , -- 需要的区域数量
    chief_referee_id INT, -- 裁判长ID
    activity_time DATETIME NOT NULL, -- 活动时间
    scoring_system VARCHAR(50) NOT NULL, -- 计分制度 21分制/30分制/11分制
    match_types VARCHAR(255) NOT NULL, -- 比赛类型: 以数组形式存储比赛的类型，如['男单', '女单', '男双', '女双', '混双']，并根据数组元素个数确定比赛进行几局
    FOREIGN KEY (venue_id) REFERENCES venue (venue_id) ON DELETE SET NULL, -- 若场地被删除，则活动中的场地ID设为NULL
    FOREIGN KEY (chief_referee_id) REFERENCES chief_referee (referee_id) ON DELETE SET NULL -- 若裁判长被删除，则裁判长ID设为NULL
);


-- 超级管理员表
CREATE TABLE admin (
    admin_id INT PRIMARY KEY auto_increment, 
    phone VARCHAR(255) NOT NULL, -- 手机号
    name VARCHAR(255) NOT NULL, -- 姓名
    password VARCHAR(255) NOT NULL -- 密码
);


-- 参赛选手表
CREATE TABLE contestant (
    contestant_id INT PRIMARY KEY auto_increment, 
    phone VARCHAR(255) NOT NULL, -- 手机号
    name VARCHAR(255) NOT NULL, -- 姓名
    password VARCHAR(255) NOT NULL, -- 密码
    is_leader BOOLEAN NOT NULL -- 是否是队长
);

-- 领队申请表
CREATE TABLE leader_application (
    application_id INT PRIMARY KEY AUTO_INCREMENT, -- 申请ID，自增主键
    contestant_id INT NOT NULL, -- 申请领队的参赛选手ID
    application_time DATETIME NOT NULL, -- 申请的时间
    is_approved BOOLEAN, -- 审核是否通过（未审核时可能为NULL）
    admin_id INT, -- 审核人的管理员ID
    approval_time DATETIME, -- 审核的时间
    FOREIGN KEY (contestant_id) REFERENCES contestant(contestant_id) ON DELETE CASCADE, -- 若参赛选手被删除，则其申请也被删除
    FOREIGN KEY (admin_id) REFERENCES admin(admin_id) ON DELETE SET NULL -- 若管理员被删除，则申请中的管理员ID设为NULL
);


-- 队伍表
CREATE TABLE team (
    team_id INT PRIMARY KEY auto_increment, 
    name VARCHAR(255) NOT NULL, -- 队伍名称
    introduction VARCHAR(255), -- 队伍介绍
    leader_name VARCHAR(255) NOT NULL, -- 领队姓名
    leader_id INT, -- 领队ID
    leader_phone VARCHAR(255) NOT NULL, -- 领队手机号
    FOREIGN KEY (leader_id) REFERENCES contestant (contestant_id) ON DELETE CASCADE -- 若领队被删除，其关联的队伍也会被自动删除
);

-- 队员关联表
CREATE TABLE team_members (
    team_id INT NOT NULL,
    contestant_id INT NOT NULL,
    PRIMARY KEY (team_id, contestant_id), -- 复合主键，确保每个队员只能在同一个队伍出现一次
    FOREIGN KEY (team_id) REFERENCES team (team_id) ON DELETE CASCADE, -- 如果队伍被删除，则相关的关联信息也会被自动删除
    FOREIGN KEY (contestant_id) REFERENCES contestant (contestant_id) ON DELETE CASCADE -- 如果参赛者被删除，其关联的队伍信息也会被自动删除
);


-- 创建活动申请表
CREATE TABLE activity_application (
    application_id INT PRIMARY KEY AUTO_INCREMENT, -- 申请ID
    event_id INT NOT NULL, -- 申请的活动ID
    team_id INT NOT NULL, -- 申请的队伍ID
    is_approved BOOLEAN, -- 是否同意该申请
    FOREIGN KEY (event_id) REFERENCES event(event_id) ON DELETE CASCADE, -- 若活动被删除，则相关的活动申请也被删除
    FOREIGN KEY (team_id) REFERENCES team(team_id) ON DELETE CASCADE, -- 若队伍被删除，则相关的活动申请也被删除
    UNIQUE (event_id, team_id) -- 添加复合唯一约束，确保同一个活动和队伍的组合是唯一的
);


-- 普通裁判表
CREATE TABLE referee (
    referee_id INT PRIMARY KEY auto_increment, 
    phone VARCHAR(255) NOT NULL, -- 手机号
    name VARCHAR(255) NOT NULL, -- 姓名
    password VARCHAR(255) NOT NULL -- 密码
);

-- 活动_裁判关联表
CREATE TABLE event_referees (
    event_id INT NOT NULL,
    referee_id INT NOT NULL,
    PRIMARY KEY (event_id, referee_id), -- 复合主键，确保每个队员只能在同一个队伍出现一次
    FOREIGN KEY (event_id) REFERENCES event (event_id) ON DELETE CASCADE, -- 如果队伍被删除，则相关的关联信息也会被自动删除
    FOREIGN KEY (referee_id) REFERENCES referee (referee_id) ON DELETE CASCADE -- 如果参赛者被删除，其关联的队伍信息也会被自动删除
);

-- 比赛表
CREATE TABLE `match` (
    match_id INT PRIMARY KEY auto_increment, 
    event_id INT NOT NULL, -- 活动ID
    team1_id INT, -- 对战队伍1 ID
    team2_id INT, -- 对战队伍2 ID
    scoring_system VARCHAR(50) NOT NULL, -- 计分制度 21分制/30分制/11分制
    referee_id INT, -- 裁判ID
    score1 VARCHAR(255),
    score2 VARCHAR(255),
    score3 VARCHAR(255),
    score4 VARCHAR(255),
    -- match5_type ENUM('男单', '女单', '男双', '女双', '混双', 'null'),
    score5 VARCHAR(255),
    score6 VARCHAR(255),
    score7 VARCHAR(255),
    winner_id INT, -- 胜利队伍ID
    FOREIGN KEY (team1_id) REFERENCES team (team_id) ON DELETE SET NULL, -- 若对战队伍被删除，则比赛记录中的队伍ID设为NULL, 
    FOREIGN KEY (team2_id) REFERENCES team (team_id) ON DELETE SET NULL, 
    FOREIGN KEY (referee_id) REFERENCES referee (referee_id) ON DELETE SET NULL,
    FOREIGN KEY (event_id) REFERENCES event (event_id) ON DELETE CASCADE -- 若活动被删除，则相关的比赛记录也被删除
);


-- 插入场地数据
INSERT INTO venue (name, address, area_count) VALUES
('体育馆', '北京市海淀区中关村大街1号', 8),
('羽毛球馆', '上海市浦东新区张江高科技园区', 6),
('运动中心', '广州市天河区珠江新城', 10);

-- 插入裁判长数据
INSERT INTO chief_referee (phone, name, password) VALUES
('13811111111', '张三', 'password123'),
('13822222222', '李四', 'password456'),
('13833333333', '王五', 'password789');

-- 插入活动数据
INSERT INTO event (name, introduction, venue_id, required_area_count, chief_referee_id, activity_time, scoring_system, match_types) VALUES
('2024年北京羽毛球锦标赛', '北京市年度羽毛球锦标赛', 1, 6, 1, '2024-06-01 09:00:00', '21分制', '["男单","女单","男双","女双","混双"]'),
('2024年上海市羽毛球友谊赛', '上海市羽毛球友谊赛', 2, 4, 2, '2024-07-15 13:00:00', '30分制', '["男单","女双","混双"]'),
('2024年广州市羽毛球邀请赛', '广州市羽毛球邀请赛', 3, 8, 3, '2024-08-20 10:00:00', '11分制', '["男单","女单","男双"]');

-- 插入超级管理员数据
INSERT INTO admin (phone, name, password) VALUES
('13844444444', '管理员1', 'admin123'),
('13855555555', '管理员2', 'admin456'),
('13866666666', '管理员3', 'admin789');

-- 插入参赛选手数据
INSERT INTO contestant (phone, name, password, is_leader) VALUES
('13877777777', '选手1', 'player123', 1),
('13888888888', '选手2', 'player456', 0),
('13899999999', '选手3', 'player789', 1),
('13800000000', '选手4', 'player000', 0),
('13811111110', '选手5', 'player111', 1);

-- 插入领队申请数据
INSERT INTO leader_application (contestant_id, application_time, is_approved, admin_id, approval_time) VALUES
(1, '2023-04-01 10:00:00', 1, 1, '2023-04-02 14:00:00'),
(3, '2023-04-03 15:00:00', 0, 2, '2023-04-04 11:00:00'),
(5, '2023-04-05 09:00:00', NULL, NULL, NULL);

-- 插入队伍数据
INSERT INTO team (name, introduction, leader_name, leader_id, leader_phone) VALUES
('队伍A', '队伍A介绍', '选手1', 1, '13877777777'),
('队伍B', '队伍B介绍', '选手3', 3, '13899999999'),
('队伍C', '队伍C介绍', '选手5', 5, '13811111110');

-- 插入队员关联数据
INSERT INTO team_members (team_id, contestant_id) VALUES
(1, 1),
(1, 2),
(2, 3),
(2, 4),
(3, 5);

-- 插入活动申请数据
INSERT INTO activity_application (event_id, team_id, is_approved) VALUES
(1, 1, 1),
(1, 2, 0),
(2, 1, 1),
(3, 3, NULL);

-- 插入普通裁判数据
INSERT INTO referee (phone, name, password) VALUES
('13900000001', '裁判1', 'referee123'),
('13900000002', '裁判2', 'referee456'),
('13900000003', '裁判3', 'referee789');

-- 插入活动_裁判关联数据
INSERT INTO event_referees (event_id, referee_id) VALUES
(1, 1),
(1, 2),
(2, 3),
(3, 1),
(3, 2),
(3, 3);

-- 插入比赛数据
INSERT INTO `match` (event_id, team1_id, team2_id, scoring_system, referee_id, score1, score2, score3, score4, score5, winner_id) VALUES
(1, 1, 2, '21分制', 1, '21-19', '16-21', '21-18', '15-21', '21-17', 1),
(2, 1, NULL, '30分制', 3, '30-29', '30-27', '25-30', NULL, NULL, 1),
(3, 3, NULL, '11分制', 2, '11-9', '8-11', '11-8', NULL, NULL, 3);