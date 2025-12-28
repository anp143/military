import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { TransactionService } from '../../service/transaction.service';
import { Base, TransactionPayload } from '../../models/transaction.model';
import { Router } from '@angular/router';


@Component({
  selector: 'app-assignment',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './assignment.component.html',
  styleUrls: ['./assignment.component.scss']
})
export class AssignmentComponent implements OnInit {

  assignmentForm!: FormGroup;
  assets: any[] = [];
  bases: Base[] = [];
  personnelList: any[] = [];

  // Arrays for tables
  assignments: any[] = [];
  purchases: any[] = [];
  transfers: any[] = [];
  expenditures: any[] = [];


  get assignmentList() {
  return this.assignments?.filter(t => t.transactionType === 'ASSIGNMENT') || [];
}


  constructor(
    private fb: FormBuilder, 
    private transactionService: TransactionService, 
    private router: Router) 
    {}

  ngOnInit(): void {

    // Form setup
    this.assignmentForm = this.fb.group({
      assetId: ['', Validators.required],
      baseId: ['', Validators.required],
      personnel: ['', Validators.required],
      quantity: ['', Validators.required]
    });

    // Load dropdowns
    this.transactionService.getAssets().subscribe(data => this.assets = data);
    this.transactionService.getBases().subscribe(data => this.bases = data);

    // Load all transactions
    this.loadTransactions();
  }

  // ========================= LOAD ALL TRANSACTIONS =========================
  loadTransactions(): void {
    this.transactionService.getAllTransactions().subscribe({
      next: (data: any[]) => {
        this.assignments = data.filter(t => t.transactionType === 'ASSIGNMENT');
        this.purchases = data.filter(t => t.transactionType === 'PURCHASE');
        this.transfers = data.filter(t => t.transactionType === 'TRANSFER');
        this.expenditures = data.filter(t => t.transactionType === 'EXPENDITURE');
      },
      error: (err) => console.error('Error fetching transactions', err)
    });
  }

  // ========================= SUBMIT ASSIGNMENT =========================
  submitAssignment(): void {
    if (this.assignmentForm.invalid) return;

    const payload: TransactionPayload = {
      quantity: this.assignmentForm.value.quantity,
      asset: { id: this.assignmentForm.value.assetId },
      fromBase: { id: this.assignmentForm.value.baseId },
      personnel: this.assignmentForm.value.personnel
    };

    this.transactionService.assignmentAsset(payload).subscribe({
      next: () => {
        alert('Assignment successful!');
        this.assignmentForm.reset();
        this.loadTransactions(); // Refresh all tables
      },
      error: (err) => {
        console.error(err);
        alert('Assignment failed: ' + (err.error?.message || err.message));
      }
    });
  }
  goBack() {
    this.router.navigate(['/dashboard']);
  }
}
