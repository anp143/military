import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class BaseService {
  private baseUrl = 'http://localhost:9094/api/bases';

  constructor(private http: HttpClient) {}

 getBases(): Observable<any[]> {
    return this.http.get<any[]>(this.baseUrl);
  }
}
