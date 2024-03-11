CREATE DATABASE TicketingSystem;
GO

USE TicketingSystem;
GO

-- Tạo bảng Tài Khoản
-- Note: có nên thay Age thành DayOfBirth/Birthday? Address có cần thiết không?
-- Đổi UserRole NVARCHAR(20) thành isPublisher BIT để xác định tài khoản có phải nhà phát hành không
CREATE TABLE Accounts (
    AccountID INT PRIMARY KEY IDENTITY(1,1),
    FirstName NVARCHAR(50) NOT NULL,
    LastName NVARCHAR(50)  NOT NULL,
    Email NVARCHAR(100) NOT NULL,
	Phone NVARCHAR(11),
	DayOfBirth Date NOT NULL,
	WalletAddress NVARCHAR(255),
    IsPublisher BIT NOT NULL default 0,
	IsActive BIT NOT NULL default 1,
	UNIQUE(Email)
);

CREATE TABLE Roles (
    RoleID INT PRIMARY KEY IDENTITY(1,1),
    RoleName NVARCHAR(20) NOT NULL,
);

CREATE TABLE Authorities (
    AuthorityID INT PRIMARY KEY IDENTITY(1,1),
    RoleID INT NOT NULL,
	AccountID INT NOT NULL,
	FOREIGN KEY (RoleID) REFERENCES Roles(RoleID),
	FOREIGN KEY (AccountID) REFERENCES Accounts(AccountID),
	UNIQUE(RoleID, AccountID)
);

-- Tạo bảng Nhà Phát Hành
-- Note: Mỗi tài khoản chỉ có thể tương ứng với một nhà phát hành
CREATE TABLE Publishers (
    PublisherID INT PRIMARY KEY IDENTITY(1,1),
    AccountID INT,
    PublisherName NVARCHAR(100) NOT NULL,
    Description NVARCHAR(1500) NOT NULL,
    Address NVARCHAR(255) NOT NULL,
	IsActive BIT NOT NULL default 1,
    FOREIGN KEY (AccountID) REFERENCES Accounts(AccountID),
    UNIQUE (AccountID)
);

CREATE TABLE TicketTypes (
    TypeID INT PRIMARY KEY IDENTITY(1,1),
	TypeName NVARCHAR(50) NOT NULL,
);

-- Tạo bảng Vé
-- Type là loại vé, vd: vé tháng xe buýt, tập vé năm xe buýt, vé tháng xe điện,... Có cần tạo bảng riêng không?
-- TicketImage là hình ảnh của vé để tạo nft, nhà phát hành upload ảnh lên
-- ShelfTime là thời gian sử dụng, tính bằng ngày
CREATE TABLE Tickets (
    TicketID INT PRIMARY KEY IDENTITY(1,1),
	TicketName NVARCHAR(50) NOT NULL,
    PublisherID INT NOT NULL,
    TicketImage NVARCHAR(150) NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    TypeID INT NOT NULL,
    ShelfTime INT NOT NULL,
    Description NVARCHAR(1000) NOT NULL,
	IsActive BIT NOT NULL DEFAULT 1,
    FOREIGN KEY (PublisherID) REFERENCES Publishers(PublisherID),
	FOREIGN KEY (TypeID) REFERENCES TicketTypes(TypeID)
);

-- Tạo bảng NFTs
-- Note: AccountId = người mua vé, TicketId = vé mà nft này đại diện cho
CREATE TABLE NFTs (
    NFTID INT IDENTITY(1,1) PRIMARY KEY,
	NFTAddress VARCHAR(1000) NOT NULL,
    AccountID INT NOT NULL,
    TicketId INT NOT NULL,
    CreateDate DATETIME NOT NULL DEFAULT getdate(),
    FOREIGN KEY (AccountID) REFERENCES Accounts(AccountID),
    FOREIGN KEY (TicketId) REFERENCES Tickets(TicketId), 
	UNIQUE(NFTAddress)
);

-- Tạo bảng Hóa Đơn
CREATE TABLE Invoices (
    InvoiceID INT PRIMARY KEY IDENTITY(1,1),
    SellerID INT NOT NULL,
	BuyerID INT NOT NULL,
    InvoiceDate DATETIME NOT NULL DEFAULT getdate(),
	NFTID INT NOT NULL,
    Amount DECIMAL(10, 2) NOT NULL,
	Payment NVARCHAR(30) NOT NULL,
    FOREIGN KEY (SellerID) REFERENCES Accounts(AccountID),
	FOREIGN KEY (BuyerID) REFERENCES Accounts(AccountID),
	FOREIGN KEY (NFTID) REFERENCES NFTs(NFTID)
);

