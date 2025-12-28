export interface Asset {
  id: number;
  name: string;
  type: string;
  quantity: number;
}

export interface Base {
  id: number;
  name?: string;
  location?: string;
}

export interface TransactionPayload {
  quantity: number;
  asset: { id: number };
  fromBase?: { id: number };
  toBase?: { id: number };
  transactionType?: string; 
      personnel?: string;
        base?: { id: number };   // ✅ add this
 


}

export interface Transaction {
  id: number;
  quantity: number;
  date: string;
  asset?: Asset;            
  transactionType: string;    
}

export interface StockReportDTO{
    baseId:number;
    baseName:string;
    assetId:number;
    assetName:string;
    baseLocation:string;
    assetType:string;
    openingBalance:number;
    purchases:string;
    transferIn:number;
    transferOut:number;
    assignments:number;
    expenditures:number;
    closingBalance:number;



}
