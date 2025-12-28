import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Asset } from '../models/asset.model';

@Injectable({ providedIn: 'root' })
export class AssetService {
  private baseUrl = 'http://localhost:9094/api/assets';

  constructor(private http: HttpClient) {}

  addAsset(asset: Asset): Observable<Asset> {
    return this.http.post<Asset>(this.baseUrl, asset);
  }

  getAllAssets(): Observable<Asset[]> {
    return this.http.get<Asset[]>(this.baseUrl);
  }

  updateAsset(id: number, asset: Asset): Observable<Asset> {
    return this.http.put<Asset>(`${this.baseUrl}/${id}`, asset);
  }

  deleteAsset(id: number): Observable<string> {
    return this.http.delete(`${this.baseUrl}/${id}`, { responseType: 'text' });
  }
}
