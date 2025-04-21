drop table if exists leave_request;
drop table if exists change_request;
drop table if exists timesheet;
drop table if exists profile;
drop table if exists account_role;
drop table if exists account;
drop table if exists role;

-- アカウントテーブル
CREATE TABLE IF NOT EXISTS account (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);

-- タイムシートテーブル
CREATE TABLE IF NOT EXISTS timesheet (
    account_id INTEGER NOT NULL,
    working_day DATE,
    working_status VARCHAR(20), -- 例: 'working', 'day off'...
    attend_time TIME, --実際の出勤時間
    leave_time TIME, --実際の退勤時間
    round_attend_time TIME, --五捨六入した出勤時間
    round_leave_time TIME, --五捨六入した退勤時間
    overtime FLOAT,  --残業時間.出退勤から自動計算
    stepout FLOAT, --中抜けに要した時間.自己申告
    finalized_flag TINYINT(1) NOT NULL, --確定フラグ。管理者への申請でtrue
    edited_flag TINYINT(1) NOT NULL, --編集済みフラグ。編集後にtrue
    requested_flag TINYINT(1) NOT NULL, --申請フラグ。申請中にtrue
    PRIMARY KEY (account_id, working_day),
    FOREIGN KEY (account_id) REFERENCES account(id)
);

-- プロフィールテーブル
CREATE TABLE IF NOT EXISTS profile (
    account_id INTEGER NOT NULL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    age INTEGER,
    sex VARCHAR(10),
    address VARCHAR(255),
    phone_number VARCHAR(20),
    joined_date DATE NOT NULL, -- 入社日
    paid_dayoff INTEGER NOT NULL, -- 有給休暇の残日数
    sub_dayoff INTEGER NOT NULL,  -- 振替休日の残日数
    FOREIGN KEY (account_id) REFERENCES account(id)
);

-- 変更申請テーブル
CREATE TABLE IF NOT EXISTS change_request (
   　id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    working_day DATE NOT NULL, 
    account_id INTEGER NOT NULL,
    working_status VARCHAR(20),
    attend_time TIME,
    leave_time TIME,
    reason VARCHAR(255),
    apply_flag TINYINT(1),
    FOREIGN KEY (account_id) REFERENCES account(id)
);

-- 休暇申請テーブル
CREATE TABLE IF NOT EXISTS leave_request (
    id INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    working_day DATE NOT NULL, 
    account_id INTEGER NOT NULL,
    reason VARCHAR(255),
    apply_flag TINYINT(1),
    FOREIGN KEY (account_id) REFERENCES account(id)
); 