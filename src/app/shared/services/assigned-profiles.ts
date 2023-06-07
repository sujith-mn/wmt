import { Inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AppConfig, APP_CONFIG } from 'src/app/app-config';
import { catchError, map, Subject } from 'rxjs';
import { NotificationService } from './notification.service';
import {  NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { assign } from '../model/assign';
import { profileList } from '../model/profileList';
@Injectable({
  providedIn: 'root'
})
export class AssignProfileService {

  private baseURL!: string;
  private availability = 'available';
  constructor(
    private http: HttpClient,
    public notificationService: NotificationService,
    private modalService: NgbModal,
    @Inject(APP_CONFIG) appConfig: AppConfig)
     {
    this.baseURL = appConfig.apiURL;
  }

  getAssignedProfileLists(id){
    return  this.http
    .get<assign[]>(this.baseURL + 'api/demands/profilelist/' +id)
    .pipe(
      map((resData: any) => {
        return resData;
      }),
      catchError((err: any) => {
        throw err;
      })
    );
  }
  statusValidation(value,id){
    return  this.http
    .put<assign[]>(this.baseURL + 'api/demands/profileStatus/'+id,value)
    .pipe(
      map((resData: any) => {
        return resData;
      }),
      catchError((err: any) => {
        throw err;
      })
    );
  }
  

}
