import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { TransactionService } from '../../service/transaction.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-purchase',
  imports:[CommonModule,FormsModule,ReactiveFormsModule],
  templateUrl: './purchase.component.html',
  styleUrls: ['./purchase.component.scss']
})
export class PurchaseComponent implements OnInit {

  purchaseForm!: FormGroup;
  assets: any[] = [];
  transactions: any[] = [];

  get purchaseList() {
  return this.transactions?.filter(t => t.transactionType === 'PURCHASE') || [];
}

  constructor(private fb: FormBuilder, private transactionService: TransactionService,private router:Router) {}

  ngOnInit(): void {
    this.purchaseForm = this.fb.group({
      assetId: ['', Validators.required],  // ✅ Must match formControlName in HTML
      quantity: [1, [Validators.required, Validators.min(1)]]
    });

    this.loadAssets();
    this.loadTransactions();
  }

  loadAssets() {
    this.transactionService.getAssets().subscribe(
      (res: any) => this.assets = res,
      (err) => console.error(err)
    );
  }

  loadTransactions() {
    this.transactionService.getAllTransactions().subscribe(
      (res: any) => this.transactions = res,
      (err) => console.error(err)
    );
  }

  submitPurchase() {
    if (this.purchaseForm.invalid) return;

    const payload = {
      asset: { id: this.purchaseForm.value.assetId },
      quantity: this.purchaseForm.value.quantity,
      transactionType: 'PURCHASE'
    };

    this.transactionService.purchaseAsset(payload).subscribe(
      (res) => {
         alert('Purchase  successful!');
        this.transactions.push(res);   // Update table
        this.purchaseForm.reset();
      },
      (err) => console.error(err)
    );
  }

  goback(){
    this.router.navigate(['/dashboard']);
  }
}
