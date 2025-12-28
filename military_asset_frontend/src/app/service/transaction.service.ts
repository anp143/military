import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Base, TransactionPayload } from '../models/transaction.model';


@Injectable({
  providedIn: 'root'
})
export class TransactionService {
  private baseUrl = 'http://localhost:9094/api/transactions';
  private baseApiUrl = 'http://localhost:9094/api/bases';  // ✅ correct endpoint for bases
  private assetApiUrl = 'http://localhost:9094/api/assets'; // ✅ correct endpoint for assets

  constructor(private http: HttpClient) {}

  // ✅ Get all transactions (for dashboard or reports)
  getAllTransactions(): Observable<any[]> {
    return this.http.get<any[]>(this.baseUrl);
  }

  // ✅ Get all assets (for dropdowns)
  getAssets(): Observable<any[]> {
    return this.http.get<any[]>(this.assetApiUrl);
  }

  // ✅ Get all bases (used in transfer dropdowns)
  getBases(): Observable<Base[]> {
    return this.http.get<Base[]>(this.baseApiUrl);
  }

  // ✅ Purchase asset
  purchaseAsset(payload: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/purchase`, payload);
  }

  // ✅ Transfer asset
  transferAsset(payload: TransactionPayload): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/transfer`, payload);
  }

  // ✅ Get all transfers only (filtered on backend or frontend)
  getAllTransfers(): Observable<any[]> {
    return this.http.get<any[]>(this.baseUrl); // no extra /transactions/all — backend already provides /api/transactions
  }

  // In transaction.service.ts
assignmentAsset(payload: TransactionPayload): Observable<any> {
  return this.http.post<any>(`${this.baseUrl}/assignment`, payload);
}

createTransaction(payload: TransactionPayload): Observable<any> {
  return this.http.post<any>(`${this.baseUrl}/expenditure`, payload);
}

getStockReport(): Observable<any[]> {
  return this.http.get<any[]>(`${this.baseUrl}/report`);
}


}
