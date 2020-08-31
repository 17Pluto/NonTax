package com.xcmis.feefax.modules.entity;

/**
 * @author fangzhe
 * @date 2020-03-31
 *
 */
public class BillRecoveryList extends CommonEntity{
        private String chrId;
        private String mainId;
        private String untaxBillnameId;
        private String untaxBillpriceId;
        private String measure;
        private String muls;
        private double price;
        private String bgnBillNo;
        private String endBillNo;
        private double storeNum;
        private String remark;
        private int isDeleted;
        private double money;
        private double booknum;
        private String occurTime;
        private int areaSource;
        private int isUseOver;
        private int isBillBack;
        private String sourceId;
        private String canusedate;

        private String untaxBillname;

        private int packnum;
        private int billLength;
        private int isend;
        private String enId;

        public String getEnId() {
                return enId;
        }

        public void setEnId(String enId) {
                this.enId = enId;
        }

        public int getIsend() {
                return isend;
        }

        public void setIsend(int isend) {
                this.isend = isend;
        }

        public int getPacknum() {
                return packnum;
        }

        public void setPacknum(int packnum) {
                this.packnum = packnum;
        }

        public int getBillLength() {
                return billLength;
        }

        public void setBillLength(int billLength) {
                this.billLength = billLength;
        }

        public String getUntaxBillname() {
                return untaxBillname;
        }

        public void setUntaxBillname(String untaxBillname) {
                this.untaxBillname = untaxBillname;
        }

        public String getChrId() {
                return chrId;
        }

        public void setChrId(String chrId) {
                this.chrId = chrId;
        }

        public String getCanusedate() {
                return canusedate;
        }

        public void setCanusedate(String canusedate) {
                this.canusedate = canusedate;
        }

        public String getSourceId() {
                return sourceId;
        }

        public void setSourceId(String sourceId) {
                this.sourceId = sourceId;
        }

        public int getIsBillBack() {
                return isBillBack;
        }

        public void setIsBillBack(int isBillBack) {
                this.isBillBack = isBillBack;
        }

        public int getIsUseOver() {
                return isUseOver;
        }

        public void setIsUseOver(int isUseOver) {
                this.isUseOver = isUseOver;
        }

        public int getAreaSource() {
                return areaSource;
        }

        public void setAreaSource(int areaSource) {
                this.areaSource = areaSource;
        }

        public String getOccurTime() {
                return occurTime;
        }

        public void setOccurTime(String occurTime) {
                this.occurTime = occurTime;
        }

        public double getBooknum() {
                return booknum;
        }

        public void setBooknum(double booknum) {
                this.booknum = booknum;
        }

        public double getMoney() {
                return money;
        }

        public void setMoney(double money) {
                this.money = money;
        }

        public int getIsDeleted() {
                return isDeleted;
        }

        public void setIsDeleted(int isDeleted) {
                this.isDeleted = isDeleted;
        }

        public String getRemark() {
                return remark;
        }

        public void setRemark(String remark) {
                this.remark = remark;
        }

        public double getStoreNum() {
                return storeNum;
        }

        public void setStoreNum(double storeNum) {
                this.storeNum = storeNum;
        }

        public String getEndBillNo() {
                return endBillNo;
        }

        public void setEndBillNo(String endBillNo) {
                this.endBillNo = endBillNo;
        }

        public String getBgnBillNo() {
                return bgnBillNo;
        }

        public void setBgnBillNo(String bgnBillNo) {
                this.bgnBillNo = bgnBillNo;
        }

        public double getPrice() {
                return price;
        }

        public void setPrice(double price) {
                this.price = price;
        }

        public String getMuls() {
                return muls;
        }

        public void setMuls(String muls) {
                this.muls = muls;
        }

        public String getMeasure() {
                return measure;
        }

        public void setMeasure(String measure) {
                this.measure = measure;
        }

        public String getUntaxBillpriceId() {
                return untaxBillpriceId;
        }

        public void setUntaxBillpriceId(String untaxBillpriceId) {
                this.untaxBillpriceId = untaxBillpriceId;
        }

        public String getUntaxBillnameId() {
                return untaxBillnameId;
        }

        public void setUntaxBillnameId(String untaxBillnameId) {
                this.untaxBillnameId = untaxBillnameId;
        }

        public String getMainId() {
                return mainId;
        }

        public void setMainId(String mainId) {
                this.mainId = mainId;
        }
}
