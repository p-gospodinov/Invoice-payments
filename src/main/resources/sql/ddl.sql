CREATE TABLE debtor (
	id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255) NOT NULL
);

CREATE TABLE invoice (
	idInvoice INT AUTO_INCREMENT PRIMARY KEY,
	number INT NOT NULL UNIQUE KEY,
	amount FLOAT(53) NOT NULL,
	currency ENUM('BG', 'USD', 'EUR') NOT NULL,
	debtorId INT,
	expiryDate DATE NOT NULL,
	FOREIGN KEY (debtorId) REFERENCES debtor(id)
);

CREATE TABLE payments (
	idPayments INT AUTO_INCREMENT PRIMARY KEY,
	amount FLOAT(53) NOT NULL,
	currency ENUM('BG', 'USD', 'EUR') NOT NULL,
	dates DATE NOT NULL,
	invoiceId INT,
	FOREIGN KEY (invoiceId) REFERENCES invoice(idInvoice)
);
