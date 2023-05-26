import { Inject, Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { AppConfig, APP_CONFIG } from 'src/app/app-config';
import { catchError, map, Subject } from 'rxjs';
import { Demand } from '../model/demand';
import { NgForm, Validators } from '@angular/forms';
import { NotificationService } from './notification.service';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { RouterTestingModule } from '@angular/router/testing';
@Injectable({
  providedIn: 'root',
})
export class DataStorageService {
  private baseURL!: string;
  formDataVal: any
  formvalues : any
 DemandSubject = new Subject<void>();
 filterSubject = new Subject<void>();
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
  get filterRefreshneeds(){
    return this.filterSubject;
  }
  storeDemand(value: NgForm) {
    const httpOptions: Object = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Headers': '*',
      }),
      responseType: 'json',
      observe: "response" as 'body',
    };
    let body = JSON.stringify(value);
    return this.http
      .post<Demand[]>(this.baseURL + 'api/demands/', body, httpOptions)
      .pipe(
        map((resData: any) => {
          console.log(resData['body']);
          // this.DemandSubject.next(resData['body']);
          this.DemandSubject.next(resData['body']);
          this.modalService.dismissAll();
          this.notificationService.success('Demand successfully added');
          return resData['body'];
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

  filterDemand(val) {
    let variable;
    let test = [];

    const formData: any = new FormData();
    for (var key in val) {
      if (val[key]) {
        formData.append(key, val[key]);
      }
    }

    for (var pair of formData.entries()) {
      console.log(pair[0] + ', ' + pair[1]);
    }
    const httpOptions: Object = {
      headers: new HttpHeaders({
        'Access-Control-Allow-Headers': '*'
      }),
    };

    return this.http.post(this.baseURL + 'api/demands/getDemandsBySearch', formData, httpOptions)
      .pipe(
        map((resData: any) => {
          console.log(resData);
          // this.modalService.dismissAll();
          this.filterSubject.next(resData);
          //   this.notificationService.success('Demand Updated successfully ');
          return resData;
        }),
        catchError((err: any) => {

          throw err;
        })
      );
  }


  
  filterProfile(val) {
    let variable;
    let test = [];

    const formData: any = new FormData();
    for (var key in val) {
      if (val[key]) {
        formData.append(key, val[key]);
      }
    }

    for (var pair of formData.entries()) {
      console.log(pair[0] + ', ' + pair[1]);
    }
    const httpOptions: Object = {
      headers: new HttpHeaders({
        'Access-Control-Allow-Headers': '*'
      }),
    };

    return this.http.post(this.baseURL + 'profiles/getProfilesBySearch', formData, httpOptions)
      .pipe(
        map((resData: any) => {
          console.log(resData);
          // this.modalService.dismissAll();
          this.filterSubject.next(resData);
          //   this.notificationService.success('Demand Updated successfully ');
          return resData;
        }),
        catchError((err: any) => {

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
        var a = this.getAllDemands();
        console.log(a);
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

  uploadDemand(x: string | Blob){
    let formData = new FormData();
    formData.append("file",x);
    const httpOptions: Object = {
      headers: new HttpHeaders({
        'Access-Control-Allow-Headers': '*',
      }),
    };
    return this.http
    .post(this.baseURL + 'api/demands/excel/upload',formData)
    .pipe(
      map((resData: any) => {
        this.DemandSubject.next();
        this.modalService.dismissAll();
        this.notificationService.success('Demand Uploaded successfully');
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
