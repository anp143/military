import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true, // ✅ required for standalone components
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'] // ✅ fixed plural
})
export class AppComponent { // ✅ fixed class name
  title = 'military-asset';
}
