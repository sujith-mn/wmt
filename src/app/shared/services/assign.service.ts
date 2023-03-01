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
export class AssignService {

  private baseURL!: string;
  private Id:string;
  AssignSubject = new Subject<void>();
  private availability = 'available';

  constructor(
    private http: HttpClient,
    public notificationService: NotificationService,
    private modalService: NgbModal,
    @Inject(APP_CONFIG) appConfig: AppConfig)
     {
    this.baseURL = appConfig.apiURL;
  }

  get refreshneeds(){
    return this.AssignSubject;
  }


  getDemandById( Id:any){
    return  this.http
  .get<assign[]>(this.baseURL + 'api/demands/' +Id)
  .pipe(
    map((resData: any) => {
      return resData;
    }),
    catchError((err: any) => {
      throw err;
    })
  );
}

getProfileBySkill( skill:any){
  return  this.http
.get<assign[]>(this.baseURL + 'profiles/by/skill/' +skill + '/availability/' +this.availability)
.pipe(
  map((resData: any) => {
    return resData;
  }),
  catchError((err: any) => {
    throw err;
  })
);
}

getProfileByAssign(Id:any,values:assign){

  return this.http.
  put<assign[]>(this.baseURL + 'api/demands/profilelist/'  +Id,values)
  .pipe(
    map((resData:any)=>{
      this.notificationService.success('Profile Assigned to Demand successfully');
      return resData;
    }),
    catchError((err:any)=>{
      throw err;
    })
  )

}

}
