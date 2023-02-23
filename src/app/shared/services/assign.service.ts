import { Inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AppConfig, APP_CONFIG } from 'src/app/app-config';
import { catchError, map, Subject } from 'rxjs';
import { NotificationService } from './notification.service';
import {  NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { assign } from '../model/assign';
@Injectable({
  providedIn: 'root'
})
export class AssignService {

  private baseURL!: string;
  private Id:string;
  AssignSubject = new Subject<void>();


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
      this.modalService.dismissAll();
      return resData;
    }),
    catchError((err: any) => {
      this.modalService.dismissAll();
      throw err;
    })
  );
}

getProfileBySkill( skill:any){
  console.log("Skill ->" +skill);
  return  this.http
.get<assign[]>(this.baseURL + 'profiles/by/primaryskill/' +skill)
.pipe(
  map((resData: any) => {
    this.modalService.dismissAll();
    console.log(" Profile Based on skill  -->" , resData);
    return resData;
  }),
  catchError((err: any) => {
    this.modalService.dismissAll();
    throw err;
  })
);
}


}
