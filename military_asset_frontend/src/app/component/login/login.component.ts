import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { UserService } from '../../service/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  username: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private userService: UserService, private router: Router) {}

  onLogin(event: Event) {
    event.preventDefault();

    this.userService.login(this.username, this.password).subscribe({
      next: (res: any) => {
        console.log('Login Success:', res);

        // ✅ Store username and role in localStorage for dashboard usage
        if (res && res.role) {
          localStorage.setItem('userRole', res.role.toUpperCase());
          localStorage.setItem('username', res.username);
        } else {
          // Default to OFFICER if role not provided
          localStorage.setItem('userRole', 'OFFICER');
          localStorage.setItem('username', res.username || '');
        }

        // Navigate to dashboard
        this.router.navigate(['/dashboard']); 
      },
      error: (err: any) => {
        console.log('Login Failed:', err);
        this.errorMessage = 'Invalid username or password';
      }
    });
  }
}
