import { Component, NgZone } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent{
  title = 'mgmt-web';
  showHead: boolean = false;
  constructor (private zone: NgZone, private router: Router) {
    this.router.events.subscribe((event: any) => {
      if (event instanceof NavigationEnd) {
          var a = event.url.substr(1);
          console.log(a);
          if(event.url.substr(1)){
            if (event.url.substr(1) === 'login') {
              this.showHead= false;
            } else {
              this.showHead= true;
            }
          }
          else{
            this.showHead= false;
          }



      }
    });
  }

}
