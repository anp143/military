import { Component } from '@angular/core';
import { ViewportScroller } from '@angular/common';
import { RouterModule } from '@angular/router';


@Component({
  selector: 'app-home',
  imports:[RouterModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {
  constructor(private viewportScroller: ViewportScroller) {}

  scrollTo(sectionId: string) {
    this.viewportScroller.scrollToAnchor(sectionId);
  }
}
