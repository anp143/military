import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators } from "@angular/forms";

import { TransactionService } from '../../service/transaction.service';
import { AssetService } from '../../service/asset.service';
import { BaseService } from '../../service/base.service';
import { Router } from '@angular/router';
import { TransactionPayload } from '../../models/transaction.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-expenditure',
  imports: [FormsModule,CommonModule,ReactiveFormsModule],
  templateUrl: './expenditure.component.html',
  styleUrl: './expenditure.component.scss'
})
export class ExpenditureComponent implements  OnInit{
 

  expenditureForm!:FormGroup;
  assets : any[]=[];
  bases:any[]=[];
  message="";

 constructor(
    private fb: FormBuilder,
    private assetService:AssetService,
    private baseService:BaseService,
    private transactionService:TransactionService,
    private router:Router
    ) {}

  ngOnInit(): void {
      this.expenditureForm=this.fb.group({
        assetId:['', Validators.required],
        baseId:['',Validators.required],
        quantity:['', Validators.required]
      });

      this.loadAssets();
      this.loadBases();
  }
loadAssets(){
  this.assetService.getAllAssets().subscribe({
    next:(data)=>this.assets=data,
    error:(err)=>console.error('Error loading assets', err)
  });
}

loadBases(){
  this.baseService.getBases().subscribe({
    next:(data)=>this.bases=data,
    error:(err)=>console.error('Error loading bases', err)
  });
}

submitExpenditure(){
  if(this.expenditureForm.invalid) return;

  const payload:TransactionPayload={
    transactionType:'EXPENDITURE',
    quantity:this.expenditureForm.value.quantity,
    asset:{id: this.expenditureForm.value.assetId},
    base:{id: this.expenditureForm.value.baseId}
  };

 this.transactionService.createTransaction(payload).subscribe({
  next:(res:any)=>{
    this.message="Expenditure  recorded Succesfully",
    this.expenditureForm.reset();
  },

  error:(err:any)=>{
    console.error(err),
    this.message="Expenditure  recorded Succesfully";
 }
});
}
goback(){
  this.router.navigate(['/dashboard']);
}
}
