import { Component, Inject, Input, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { assign } from '../shared/model/assign';
import { ActivatedRoute, Data } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { catchError, map } from 'rxjs';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AppConfig, APP_CONFIG } from 'src/app/app-config';
import { AssignService } from '../shared/services/assign.service';
import { DataSource } from '@angular/cdk/collections';
// import { DataSource } from '@angular/cdk/collections';
@Component({
  selector: 'app-assign',
  templateUrl: './assign.component.html',
  styleUrls: ['./assign.component.css']
})
export class AssignComponent implements OnInit {


  displayedColumns:string[] = ['manager','created','priority','skill','status'];
  dataSource: assign[] = [];
  profiles:any;
  Id: string;
  paramsId: any;
  skill:string;
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
    }
    );
    this.getDemandData();
     

    
  }
  getDemandData() {
    return this.assignService.getDemandById(this.Id).subscribe(
      {
        next: (result: assign) => {
          this.skill=result.skill;
          this.dataSource.push(result);
          this.dataSource = [...this.dataSource];
          this.getProfile();
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

  getProfile(){
    console.log(this.skill);
    return this.assignService.getProfileBySkill(this.skill).subscribe(
      {
        next: (result: any) => {
          this.profiles=result;
          console.log("profiles : " +result);
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

