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
    console.log("ID in assign.service.ts ->",Id);
    // DemandId = Id;
    return  this.http
  .get<assign[]>(this.baseURL + 'api/demands/' +Id)
  .pipe(
    map((resData: any) => {
      this.modalService.dismissAll();
      console.log(" Assign Data -->" , resData);
      return resData;
    }),
    catchError((err: any) => {
      this.modalService.dismissAll();
      throw err;
    })
  );
  // .subscribe((RestData : assign)=>{
  //   this.AssignDemand= RestData;
  // })
  // ;
}
}
