import { Component } from '@angular/core';
import { SafeResourceUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  showMyContainer=false;
  viewMode = 'map';
  val = true;
  ngOnInit(): void {
    localStorage.setItem('login','false');
  }

  onclick(){
    this.showMyContainer=!this.showMyContainer
  }
  calculate(){

    if(this.val){

      this.val = false;

    }

    else{

      this.val = true;

    }

 

  }
}
