import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'profileApp';

  titlebar: string = '';
  constructor(private route: Router) { }
  setHeader() {
    let path = this.route.url.split('/')[1];
    if (path == 'search') {
      this.titlebar = "Profile Search";
    }
    if (path == '') {
      this.titlebar = "Home";
    }
  }
}

