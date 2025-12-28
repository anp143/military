import { Routes } from '@angular/router';

// Import all components
import { DashboardComponent } from './component/dashboard/dashboard.component';
import { AssetPageComponent } from './component/asset-page/asset-page.component';
import { PurchaseComponent } from './component/purchase/purchase.component';
import { LoginComponent } from './component/login/login.component';
import { HomeComponent } from './component/home/home.component';
import { AddAssetComponent } from './component/add-asset/add-asset.component';
import { ViewAssetComponent } from './component/view-assets/view-assets.component';
import { TransferComponent } from './component/transfer/transfer.component';
import { AssignmentComponent } from './component/assignment/assignment.component';
import { ExpenditureComponent } from './component/expenditure/expenditure.component';
import { ReportsComponent } from './component/reports/reports.component';

export const routes: Routes = [

  // Default route → redirects to Home page
  { path: '', redirectTo: 'home', pathMatch: 'full' },

  // Home page
  { path: 'home', component: HomeComponent },

  // Authentication
  { path: 'login', component: LoginComponent },

  // Dashboard
  { path: 'dashboard', component: DashboardComponent },

  // Asset Management
  { path: 'asset', component: AssetPageComponent },
  {path:'add-asset', component:AddAssetComponent},
  {path:'view-asset', component:ViewAssetComponent},

  // Purchase / Transfer / Assignment / Expenditure / Reports
  { path: 'purchase', component: PurchaseComponent },
  {path:'transfer',component:TransferComponent},

  {path:'assignment', component:AssignmentComponent},

  {path:'expenditure',component:ExpenditureComponent},
  {path:'report',component:ReportsComponent},

  // Wildcard route → if path not found, redirect to Home
  { path: '**', redirectTo: 'dashboard' }
];
