import { Component, Inject, Input , OnInit} from '@angular/core';
import { FormGroup } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { demandData } from '../search/search.component';
import { assign } from '../shared/model/assign';
import { Demand } from '../shared/model/demand';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { catchError, map } from 'rxjs';

import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AppConfig, APP_CONFIG } from 'src/app/app-config';
@Component({
  selector: 'app-assign',
  templateUrl: './assign.component.html',
  styleUrls: ['./assign.component.css']
})
export class AssignComponent implements OnInit {
  private baseURL!: string;
@Input('Variable') RowData : any;

Id:string;
paramsId: any;
AssignDemand : assign;
constructor(private route: ActivatedRoute , private httpClient: HttpClient,
  private modalService: NgbModal,
  @Inject(APP_CONFIG) appConfig: AppConfig
) {
  this.baseURL = appConfig.apiURL;
}
ngOnInit() {
   console.log("Assign component : " ,+this.RowData);
  this.route.queryParamMap.subscribe((params) => {
  this.paramsId ={ ...params.keys, ...params };
  this.Id = this.paramsId.params.Id;

  
 
}
  );
  // this.getData();

  console.log(" this.AssignDemand ->" ,  this.getData());
}

getData(){
  return  this.httpClient
  .get<assign>(this.baseURL + 'api/demands/' +this.Id)
  .pipe(
    map((resData: assign) => {
      this.modalService.dismissAll();
      console.log(" Assign Data -->" , resData);
      return resData;
    }),
    catchError((err: any) => {
      this.modalService.dismissAll();
      throw err;
    })
  )
  .subscribe((RestData : assign)=>{
    this.AssignDemand= RestData;
  })
  ;
  
}

}
// function getData() {
//   throw new Error('Function not implemented.');
// }

