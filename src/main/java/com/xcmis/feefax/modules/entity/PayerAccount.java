package com.xcmis.feefax.modules.entity;

public class PayerAccount extends CommonEntity{
	private String accountNo;
	private String accountName;
	private String bankCode;

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	private String bankName;

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getIenId() {
		return ienId;
	}

	public void setIenId(String ienId) {
		this.ienId = ienId;
	}

	public int getPayorReceive() {
		return payorReceive;
	}

	public void setPayorReceive(int payorReceive) {
		this.payorReceive = payorReceive;
	}

	private String bankId;
	private String ienId;
	private int payorReceive;
	private int accountType;



	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public int getAccountType() {
		return accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}


}
