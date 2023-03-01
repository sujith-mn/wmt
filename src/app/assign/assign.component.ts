import { Component, Inject, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { assign } from '../shared/model/assign';
import { ActivatedRoute, Data } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AssignService } from '../shared/services/assign.service';
import { profileList } from '../shared/model/profileList';
import { Profile } from '../shared/model/profile';

@Component({
  selector: 'app-assign',
  templateUrl: './assign.component.html',
  styleUrls: ['./assign.component.css']
})
export class AssignComponent implements OnInit {

  ShowColumns: string[] = [ 'name', 'primarySkill', 'location', 'availability', 'source','actions','assign']

   displayedColumns:string[] = ['manager','created','priority','skill','status'];
  dataSource: assign[] = [];
  profiles:any;
  Id: string;
  paramsId: any;
  skill:string;
  DetailProfileForm: [];

  constructor(
    private route: ActivatedRoute,
    private httpClient: HttpClient,
    private modalService: NgbModal,
    private fb: FormBuilder,
    private assignService: AssignService

  ) {
  }



  detailForm:FormGroup = this.fb.group({
    id:[''],
    name: [''],
    source: [''],
    location:[''],
    availability:[''],
    primarySkill: ['']
  })

  ngOnInit(){
    this.route.queryParamMap.subscribe((params) => {
      this.paramsId = { ...params.keys, ...params };
      this.Id = this.paramsId.params.Id;
    }
    );
    this.getDemandData();
    //this.fetchSelectedItems();




  }

  getDemandData() {

    return this.assignService.getDemandById(this.Id).subscribe(
      {
        next: (result: assign) => {
          this.skill=result.skill;

          this.dataSource.push(result);
          this.dataSource = [...this.dataSource];
          console.log("Datasource ",this.dataSource);
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
    //console.log(this.skill);
    return this.assignService.getProfileBySkill(this.skill).subscribe(
      {
        next: (result: any) => {
          this.profiles=result;

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
  SubmitProfiles(){
      this.fetchSelectedItems()
  }
  fetchSelectedItems() {
    this.DetailProfileForm = this.profiles.filter((value: { isChecked: any; }, index: any) => {

      return value.isChecked });
      this.dataSource[0].profilesList =  this.DetailProfileForm;
    console.log(this.dataSource[0]);
   }
  GetProfileOnSubmit(){
   return this.assignService.getProfileByAssign(this.Id,this.dataSource[0]).subscribe(
    {
    next: (result: any) => {

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
    console.log(this.DetailProfileForm);
  }


  //Detail Start
  openProfile(targetModal: any, profiles:any) {
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'md'
    });
    console.log("Profiles  " , profiles);
    let editedDemandValues = {

      id: profiles.id,
      name: profiles.name,
      source: profiles.source,
      location:profiles.location,

      availability:profiles.availability,
      primarySkill: profiles.primarySkill
      // proposedBy: profiles.proposedBy,

    }
    console.log("editedDemandValues : " , editedDemandValues);
    this.detailForm.patchValue(editedDemandValues);

  }
  //Detail End
}

