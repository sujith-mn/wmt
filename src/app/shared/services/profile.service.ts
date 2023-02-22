import { Inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AppConfig, APP_CONFIG } from 'src/app/app-config';
import { catchError, map, Subject } from 'rxjs';
import { NotificationService } from './notification.service';
import {  NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Profile } from '../model/profile';

@Injectable({
  providedIn: 'root'
})
export class ProfileService {
  private baseURL!: string;
  ProfileSubject = new Subject<void>();
  constructor(
    private http: HttpClient,
    public notificationService: NotificationService,
    private modalService: NgbModal,
    @Inject(APP_CONFIG) appConfig: AppConfig
  ) { 
    this.baseURL = appConfig.apiURL;
  }

  get refreshneeds(){
    return this.ProfileSubject;
  }

  getAllProfiles(){
    return this.http
      .get<Profile[]>(this.baseURL + 'profiles')
      .pipe(
        map((resData: any) => {
          this.modalService.dismissAll();
          return resData;
        }),
        catchError((err: any) => {
          this.modalService.dismissAll();
          throw err;
        })
      );
  }


  file:any;
  selectFile(event: any){
    console.log(event);
    this.file = event.target.files[0];
    console.log(this.file);
  }

  uploadProfile(x: string | Blob){
    let formData = new FormData();
    formData.append("file",x);
    const httpOptions: Object = {
      headers: new HttpHeaders({
        'Access-Control-Allow-Headers': '*',
      }),
     
    };
    
    

    return this.http
    .post(this.baseURL + 'profiles/excel/upload',formData, httpOptions)
    .pipe(
      map((resData: any) => {
        this.ProfileSubject.next();
        this.modalService.dismissAll();
        this.notificationService.success('Profile Uploaded successfully');
        return resData;
      }),
      catchError((err: any) => {
        this.modalService.dismissAll();
        this.notificationService.error('Unable to proceed');
        throw err;
      })
    );
  }
}
