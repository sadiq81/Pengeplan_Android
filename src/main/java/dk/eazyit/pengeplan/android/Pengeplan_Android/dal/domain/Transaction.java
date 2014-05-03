package dk.eazyit.pengeplan.android.Pengeplan_Android.dal.domain;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author
 */
@DatabaseTable
public class Transaction {

    public static final String PAPER_NAME = "paperName";

    @DatabaseField(id = true)
    private Long id;

    @DatabaseField
    private String transactionType;

    @DatabaseField
    private Date date;

    @DatabaseField(columnName = Transaction.PAPER_NAME)
    private String paperName;

    @DatabaseField
    private String stockExchange;

    @DatabaseField
    private String currency;

    @DatabaseField
    private BigDecimal numberOfItems;

    @DatabaseField
    private BigDecimal valuation;

    @DatabaseField
    private BigDecimal amount;

    @DatabaseField
    private String legalEntity;

    @DatabaseField
    private String ownedAccount;

    @DatabaseField
    private BigDecimal localAmount;

    public Transaction() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPaperName() {
        return paperName;
    }

    public void setPaperName(String paperName) {
        this.paperName = paperName;
    }

    public String getStockExchange() {
        return stockExchange;
    }

    public void setStockExchange(String stockExchange) {
        this.stockExchange = stockExchange;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(BigDecimal numberOfItems) {
        this.numberOfItems = numberOfItems;
    }

    public BigDecimal getValuation() {
        return valuation;
    }

    public void setValuation(BigDecimal valuation) {
        this.valuation = valuation;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getLegalEntity() {
        return legalEntity;
    }

    public void setLegalEntity(String legalEntity) {
        this.legalEntity = legalEntity;
    }

    public String getOwnedAccount() {
        return ownedAccount;
    }

    public void setOwnedAccount(String ownedAccount) {
        this.ownedAccount = ownedAccount;
    }

    public BigDecimal getLocalAmount() {
        return localAmount;
    }

    public void setLocalAmount(BigDecimal localAmount) {
        this.localAmount = localAmount;
    }
}
