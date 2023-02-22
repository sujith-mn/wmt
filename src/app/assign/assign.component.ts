import { Component, Inject, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { assign } from '../shared/model/assign';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { catchError, map } from 'rxjs';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AppConfig, APP_CONFIG } from 'src/app/app-config';
import { AssignService } from '../shared/services/assign.service';

export class assignData {
  constructor(
    public manager: string,
    public created: string,
    public endDate: string,
    public ageing: string,
    public priority: string,
    public skill: string,
    public status: string,
    public id?: string
  ) { }
}
@Component({
  selector: 'app-assign',
  templateUrl: './assign.component.html',
  styleUrls: ['./assign.component.css']
})
export class AssignComponent implements OnInit {

  assign: assignData[];
  dataSource: MatTableDataSource<assignData>;
  displayedColumns:string[] = ['manager','created','priority','skill','status'];

  Id: string;
  paramsId: any;
  constructor(
    private route: ActivatedRoute,
    private httpClient: HttpClient,
    private modalService: NgbModal,
    private assignService: AssignService

  ) {
  }
  ngOnInit(): void {
    this.route.queryParamMap.subscribe((params) => {
      this.paramsId = { ...params.keys, ...params };
      this.Id = this.paramsId.params.Id;
      console.log("Demand by id ->", this.getDemandData());

    }
    );
    this.assignService.refreshneeds.subscribe(() => {
      this.getDemandData();

    })
    this.getDemandData();
  }

  // getData(){
  //   return  this.httpClient
  //   .get<assign>(this.baseURL + 'api/demands/' +this.Id)
  //   .pipe(
  //     map((resData: assign) => {
  //       this.modalService.dismissAll();
  //       console.log(" Assign Data -->" , resData);
  //       return resData;
  //     }),
  //     catchError((err: any) => {
  //       this.modalService.dismissAll();
  //       throw err;
  //     })
  //   )
  //   .subscribe((RestData : assign)=>{
  //     this.AssignDemand= RestData;
  //   })
  //   ;

  // }

  getDemandData() {
    return this.assignService.getDemandById(this.Id).subscribe(

      {
        next: (result: any) => {
          console.log("GetDemandData", result);
          this.assign = result;
          this.dataSource = new MatTableDataSource<assignData>(this.assign);

        },
        error: (err: any) => {
          console.log(err);
        },
        complete: () => {
          console.log('complete');
        }
      }
    )
  }

}

