package com.militaryassetmanagementsystem.dto;

public class StockReportDTO {

    private Long assetId;
    private String assetName;
    private String assetType;
    private Long baseId;
    private String baseName;
    private String baseLocation;

    private int openingBalance;
    private int purchases;
    private int transfersIn;
    private int transfersOut;
    private int assignments;
    private int expenditures;
    private int closingBalance;

    // ✅ Default constructor
    public StockReportDTO() {}

    // ✅ Constructor with all fields
    public StockReportDTO(Long assetId, String assetName, String assetType,
                          Long baseId, String baseName, String baseLocation,
                          int openingBalance, int purchases, int transfersIn,
                          int transfersOut, int assignments, int expenditures, int closingBalance) {
        this.assetId = assetId;
        this.assetName = assetName;
        this.assetType = assetType;
        this.baseId = baseId;
        this.baseName = baseName;
        this.baseLocation = baseLocation;
        this.openingBalance = openingBalance;
        this.purchases = purchases;
        this.transfersIn = transfersIn;
        this.transfersOut = transfersOut;
        this.assignments = assignments;
        this.expenditures = expenditures;
        this.closingBalance = closingBalance;
    }

    // ✅ Constructor for partial data
    public StockReportDTO(Long assetId, String assetName, String assetType,
                          Long baseId, String baseName, String baseLocation) {
        this(assetId, assetName, assetType, baseId, baseName, baseLocation,
             0, 0, 0, 0, 0, 0, 0);
    }

    // ✅ Getters and Setters
    public Long getAssetId() { return assetId; }
    public void setAssetId(Long assetId) { this.assetId = assetId; }

    public String getAssetName() { return assetName; }
    public void setAssetName(String assetName) { this.assetName = assetName; }

    public String getAssetType() { return assetType; }
    public void setAssetType(String assetType) { this.assetType = assetType; }

    public Long getBaseId() { return baseId; }
    public void setBaseId(Long baseId) { this.baseId = baseId; }

    public String getBaseName() { return baseName; }
    public void setBaseName(String baseName) { this.baseName = baseName; }

    public String getBaseLocation() { return baseLocation; }
    public void setBaseLocation(String baseLocation) { this.baseLocation = baseLocation; }

    public int getOpeningBalance() { return openingBalance; }
    public void setOpeningBalance(int openingBalance) { this.openingBalance = openingBalance; }

    public int getPurchases() { return purchases; }
    public void setPurchases(int purchases) { this.purchases = purchases; }

    public int getTransfersIn() { return transfersIn; }
    public void setTransfersIn(int transfersIn) { this.transfersIn = transfersIn; }

    public int getTransfersOut() { return transfersOut; }
    public void setTransfersOut(int transfersOut) { this.transfersOut = transfersOut; }

    public int getAssignments() { return assignments; }
    public void setAssignments(int assignments) { this.assignments = assignments; }

    public int getExpenditures() { return expenditures; }
    public void setExpenditures(int expenditures) { this.expenditures = expenditures; }

    public int getClosingBalance() { return closingBalance; }
    public void setClosingBalance(int closingBalance) { this.closingBalance = closingBalance; }

    // ✅ Utility: auto-calculate closing
    public void calculateClosingBalance() {
        this.closingBalance = openingBalance + purchases + transfersIn
                             - transfersOut - assignments - expenditures;
    }

    @Override
    public String toString() {
        return "StockReportDTO{" +
                "assetId=" + assetId +
                ", assetName='" + assetName + '\'' +
                ", assetType='" + assetType + '\'' +
                ", baseId=" + baseId +
                ", baseName='" + baseName + '\'' +
                ", baseLocation='" + baseLocation + '\'' +
                ", openingBalance=" + openingBalance +
                ", purchases=" + purchases +
                ", transfersIn=" + transfersIn +
                ", transfersOut=" + transfersOut +
                ", assignments=" + assignments +
                ", expenditures=" + expenditures +
                ", closingBalance=" + closingBalance +
                '}';
    }
}
