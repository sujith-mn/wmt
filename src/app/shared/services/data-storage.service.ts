import { Inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AppConfig, APP_CONFIG } from 'src/app/app-config';
import { catchError, map, Subject } from 'rxjs';
import { Demand } from '../model/demand';
import { NgForm } from '@angular/forms';
import { NotificationService } from './notification.service';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
@Injectable({
  providedIn: 'root',
})
export class DataStorageService {
  private baseURL!: string;
 DemandSubject = new Subject<void>();
  constructor(
    private http: HttpClient,
    public notificationService: NotificationService,
    private modalService: NgbModal,
    @Inject(APP_CONFIG) appConfig: AppConfig
  ) {
    this.baseURL = appConfig.apiURL;
  }

  get refreshneeds(){
    return this.DemandSubject;
  }
  storeDemand(value: NgForm) {
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
      .post<Demand[]>(this.baseURL + 'api/demands/', body, httpOptions)
      .pipe(
        map((resData: any) => {
          console.log(resData);
          this.DemandSubject.next();
          this.modalService.dismissAll();
          this.notificationService.success('Demand successfully added');
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

  getAllDemands(){
    return this.http
      .get<Demand[]>(this.baseURL + 'api/demands/')
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
  editDemand(id: any,values: any){
    return this.http.put<Demand>(this.baseURL + 'api/demands/'+id,values)
    .pipe(
      map((resData: any) => {
        this.modalService.dismissAll();
        this.DemandSubject.next();
          this.notificationService.success('Demand Updated successfully ');
        return resData;
      }),
      catchError((err: any) => {
        this.modalService.dismissAll();
        this.notificationService.error('Unable to proceed');
        throw err;
      })
    );
  }
  deleteDemand(id:any){
    console.log("delete id ",id);
    return this.http.delete<Demand>(this.baseURL + 'api/demands/'+id)
    .pipe(
      map((resData: any) => {
        this.modalService.dismissAll();
        this.DemandSubject.next();
        this.notificationService.success('Demand Deleted successfully');
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
