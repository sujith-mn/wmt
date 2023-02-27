import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginService } from '../shared/services/login.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  isLoggedIn$!: Observable<boolean>;
  username: string | undefined;

  constructor(private loginService: LoginService) { }

  ngOnInit(): void {
    this.isLoggedIn$ = this.loginService.isLoggedIn;
    let getName = localStorage.getItem('loggedUser');
    let splitted = getName?.toString().split('@')[0];
    this.username = splitted;
  }
  logout(){
    localStorage.clear();
    this.loginService.logout();
  }
}
