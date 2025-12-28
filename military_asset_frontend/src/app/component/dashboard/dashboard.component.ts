import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {
  userRole: string = '';
  isAdmin = false;
  isCommander = false;
  isOfficer = false;

  constructor(private router: Router) {}

  ngOnInit(): void {
    const storedRole = localStorage.getItem('userRole');
    console.log('Dashboard role:', storedRole);

    this.userRole = storedRole ? storedRole : 'OFFICER';
    this.isAdmin = this.userRole === 'ADMIN';
    this.isCommander = this.userRole === 'COMMANDER';
    this.isOfficer = this.userRole === 'OFFICER';
  }

  navigateTo(route: string) {
    this.router.navigate([`/${route}`]);
  }
}
