import { Inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AppConfig, APP_CONFIG } from 'src/app/app-config';
import { login } from '../model/login';
import { catchError, map } from 'rxjs/operators';
import { Router } from '@angular/router';
import { BehaviorSubject } from 'rxjs';
import { NotificationService } from './notification.service';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private loggedIn: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  private baseURL!: string;
  constructor(
    public notificationService: NotificationService,
    private router: Router,
    private http: HttpClient,
    @Inject(APP_CONFIG) appConfig: AppConfig) {
    this.baseURL = appConfig.apiURL;
  }
  get isLoggedIn() {
    return this.loggedIn.asObservable();
  }
  login(value: login) {

    const httpOptions: Object = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Headers': '*',
      }),
      responseType: 'json',
      observe: 'response',
    };
    let body = JSON.stringify(value);

    return this.http
      .post(this.baseURL + 'registrations/validate', body, httpOptions)
      .pipe(
        map((resData: any) => {

          return resData;
        }),
        catchError((err: any) => {
          this.notificationService.error("username or password incorrect");
          throw err;
        })
      );
  }

  logout() {
    this.loggedIn.next(false);
    this.router.navigate(['/login']);
  }

}
