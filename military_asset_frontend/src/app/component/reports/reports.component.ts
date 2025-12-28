import { Component, OnInit } from '@angular/core';
import { TransactionService } from '../../service/transaction.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-reports',
  imports:[CommonModule],
  templateUrl: './reports.component.html',
  styleUrls: ['./reports.component.scss']
})
export class ReportsComponent implements OnInit {

  reports: any[] = [];

  constructor(private transactionService: TransactionService,private router:Router) {}

  ngOnInit(): void {
    this.loadStockReport();
  }

  loadStockReport(): void {
    this.transactionService.getStockReport().subscribe({
      next: (data) => {
        this.reports = data;
        console.log('Stock Report Loaded:', data);
      },
      error: (err) => {
        console.error('Error loading stock report:', err);
      }
    });
  }
   goback(){
    this.router.navigate(['/dashboard']);
   }
}
