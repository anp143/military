import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule, FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { TransactionService } from '../../service/transaction.service';
import { Base } from '../../models/transaction.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-transfer',
  standalone: true,
  imports: [FormsModule, CommonModule, ReactiveFormsModule],
  templateUrl: './transfer.component.html',
  styleUrls: ['./transfer.component.scss']
})
export class TransferComponent implements OnInit {

  transferForm: FormGroup;
  assets: any[] = [];
  bases: Base[] = [];
  transferHistory: any[] = [];



get transferList() {
    return this.transferHistory?.filter(t => t.transactionType === 'TRANSFER') || [];
  }

  constructor(private fb: FormBuilder, private transactionService: TransactionService,private router:Router) {
    this.transferForm = this.fb.group({
      assetId: ['', Validators.required],
      quantity: ['', [Validators.required, Validators.min(1)]],
      fromBaseId: ['', Validators.required],
      toBaseId: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    // Fetch all assets for dropdown
    this.transactionService.getAssets().subscribe({
      next: (data) => this.assets = data,
      error: (err) => console.error('Error fetching assets:', err)
    });

    // Fetch all bases for dropdown
    this.transactionService.getBases().subscribe({
      next: (data) => this.bases = data,
      error: (err) => console.error('Error fetching bases:', err)
    });

    // Load transfer history initially
    this.loadTransfers();
  }

  // ✅ Fixed method name & reference
  loadTransfers() {
   this.transactionService.getAllTransfers().subscribe({
  next: (data: any[]) => {
    this.transferHistory = data;
  },
  error: (err: any) => {
    console.error('Error fetching transfer history:', err);
  }
});

  }

  // ✅ Transfer submit logic
  submitTransfer() {
    if (this.transferForm.invalid) {
      alert('Please fill all required fields correctly.');
      return;
    }

    const payload = {
      quantity: this.transferForm.value.quantity,
      asset: { id: this.transferForm.value.assetId },
      fromBase: { id: this.transferForm.value.fromBaseId },
      toBase: { id: this.transferForm.value.toBaseId }
    };

    this.transactionService.transferAsset(payload).subscribe({
      next: (res: any) => {
        alert('Transfer successful!');
        this.transferForm.reset();
        this.loadTransfers(); // ✅ Refresh table after transfer
      },
      error: (err: any) => {
        console.error('Transfer failed:', err);
        alert('Transfer failed: ' + (err.error?.message || err.message));
      }
    });
  }
  goback(){
    this.router.navigate(['/dashboard'])
  }
}
