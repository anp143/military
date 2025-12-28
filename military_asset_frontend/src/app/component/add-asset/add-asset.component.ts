import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AssetService } from '../../service/asset.service';
import { BaseService } from '../../service/base.service';
import { Asset } from '../../models/asset.model';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';


@Component({
  selector: 'app-add-asset',
  imports:[CommonModule,FormsModule],
  templateUrl: './add-asset.component.html',
  styleUrls: ['./add-asset.component.scss']
})
export class AddAssetComponent implements OnInit {

  asset: Asset = { name: '', type: '', quantity: 0, base: { id: 0 } };
  bases: any[] = [];
  loading = false;
  message = '';

  constructor(
    private assetService: AssetService,
    private baseService: BaseService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.baseService.getBases().subscribe({
      next: res => this.bases = res,
      error: err => console.error(err)
    });
  }

  onSubmit() {
    if (!this.asset.name || !this.asset.type || this.asset.quantity === null || !this.asset.base?.id) {
      this.message = 'Please fill all required fields';
      return;
    }

    this.loading = true;

    // send only base.id
    const payload: Asset = {
      name: this.asset.name,
      type: this.asset.type,
      quantity: this.asset.quantity,
      base: { id: this.asset.base.id }
    };

    this.assetService.addAsset(payload).subscribe({
      next: res => {
        this.message = 'Asset added successfully!';
        this.loading = false;
        this.asset = { name: '', type: '', quantity: 0, base: { id: 0 } };
      },
      error: err => {
        console.error(err);
        this.message = 'Error adding asset: ' + err.message;
        this.loading = false;
      }
    });
  }

  goBack() {
    this.router.navigate(['/dashboard']);
  }
}
