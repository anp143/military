import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ViewAssetComponent } from '../view-assets/view-assets.component';
import { AddAssetComponent } from '../add-asset/add-asset.component';

@Component({
  selector: 'app-asset-page',
  standalone: true,
  imports: [CommonModule,ViewAssetComponent,AddAssetComponent],
  templateUrl: './asset-page.component.html',
  styleUrls: ['./asset-page.component.scss']
})
export class AssetPageComponent {
  showForm = false;  // false → show list, true → show form
}
