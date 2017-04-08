package com.teambeer.starlingApi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionsOut {

    public String merchantname;
    public String merchantcode;
    public double transactionamount;
    public LocalDateTime transactiontime;

    public TransactionsOut() {
    }

    public String getMerchantname() {
        return merchantname;
    }

    public void setType(String Merchantname) {
        this.merchantname = Merchantname;
    }

    public String getMerchantcode() {
        return merchantcode;
    }

    public void setMerchantcode(String Merchantcode) {
        this.merchantcode = Merchantcode;
    }
    
    public LocalDateTime getTransactiontime() {
        return transactiontime;
    }

    public void setTransactiontime(LocalDateTime Transactiontime) {
        this.transactiontime = Transactiontime;
    }
    
    public double getTransactionamount() {
        return transactionamount;
    }

    public void setTransactionamount(double Transactionamount) {
        this.transactionamount = Transactionamount;
    }    
    
    @Override
    public String toString() {
        return "TransactionsOut{" +
                "merchantname='" + merchantname + '\'' +
                "merchantcode='" + merchantcode + '\'' +
                "transactionamount='" + transactionamount + '\'' +
                ", transactiontime=" + transactiontime +
                '}';
    }
}
