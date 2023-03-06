import { Inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AppConfig, APP_CONFIG } from 'src/app/app-config';
import { catchError, map, Subject } from 'rxjs';
import { NotificationService } from './notification.service';
import {  NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Profile } from '../model/profile';
import { NgForm } from '@angular/forms';

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
    const httpOptions: Object = {
      headers: new HttpHeaders({
        'Access-Control-Allow-Headers': '*',
      }),
      observe: 'response',
    };
    return this.http
      .get<Profile[]>(this.baseURL + 'profiles/')
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

  //Upload Resume 

  uploadResume(x: string | Blob){
    let formData = new FormData();
    formData.append("file",x);
    const httpOptions: Object = {
      headers: new HttpHeaders({
        'Access-Control-Allow-Headers': '*',
      }),
    };
    return this.http
    .put(this.baseURL + 'profiles/{id}/resume',formData, httpOptions)
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
  // Add New profiles start .
    NewProfile(value: NgForm){
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
      .post<Profile[]>(this.baseURL + 'profiles/add',value,httpOptions)
      .pipe(
        map((resData: any) => {
          console.log(resData);
          this.ProfileSubject.next();
          this.modalService.dismissAll();
          this.notificationService.success('Profile successfully added');
          return resData;
        }),
        catchError((err: any) => {
          console.log(err);
          this.modalService.dismissAll();
          this.notificationService.error('Unable to proceed');
          throw err;
        })
      );
  }
  // Add New profiles end.

  //Profile Edit start
  editProfile(id: any,values: any){
    console.log("Edit profile : " ,values)
    return this.http.put<Profile>(this.baseURL + 'profiles/'+id,values)
    .pipe(
      map((resData: any) => {
        this.modalService.dismissAll();
        this.ProfileSubject.next();
          this.notificationService.success('Profile Updated successfully ');
        return resData;
      }),
      catchError((err: any) => {
        this.modalService.dismissAll();
        this.notificationService.error('Unable to proceed');
        throw err;
      })
    );
  }
  //Profile Edit end

  deleteProfile(id:any){
    const httpOptions : Object = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
      }),
      responseType: 'text',
      observe: 'response',
    };
    return this.http.delete(this.baseURL + 'profiles/'+id,httpOptions)

    .pipe(
      map((resData: any) => {
        this.modalService.dismissAll();
        this.ProfileSubject.next();
        this.notificationService.success('Profile Deleted successfully');
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
