import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { AssetService } from '../../service/asset.service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';


@Component({
  selector: 'app-view-asset',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './view-assets.component.html',
  styleUrls: ['./view-assets.component.scss']
})
export class ViewAssetComponent implements OnInit {
  assets: any[] = [];

  @Output() addAsset = new EventEmitter<void>();

  constructor(private assetService: AssetService, private router:Router) {}

  ngOnInit() {
    this.loadAssets();
  }

  loadAssets() {
    this.assetService.getAllAssets().subscribe({
      next: (data) => this.assets = data,
      error: (error) => {
        console.error('Error loading assets', error);
        alert('Failed to load assets. Please try again later.');
      }
    });
  }

  deleteAsset(id: number) {
    if (!confirm('Are you sure you want to delete this asset?')) return;

    this.assetService.deleteAsset(id).subscribe({
      next: () => {
        alert('Asset Deleted Successfully'); // backend message like "Cannot delete asset because it is linked to transactions."
        this.loadAssets();
      },
      error: (err) => {
        if(err.status === 409) {
         alert('Cannot delete asset — it is used in transactions.');
    } else {
      alert('Delete failed: ' + err.message);
    }
  }
});
  }
  goback(){
    this.router.navigate(["/dashboard"]);
  }
}
