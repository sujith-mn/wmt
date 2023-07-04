import { Component, Inject, Input, OnInit, QueryList, ViewChild, ViewChildren } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { assign } from '../shared/model/assign';
import { ActivatedRoute, Data, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { AssignService } from '../shared/services/assign.service';
import { profileList } from '../shared/model/profileList';
import { Profile } from '../shared/model/profile';
import { ProfileData } from '../profile/profile.component';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatCheckbox, MatCheckboxChange, MatCheckboxModule } from '@angular/material/checkbox';
@Component({
  selector: 'app-assign',
  templateUrl: './assign.component.html',
  styleUrls: ['./assign.component.css']
})
export class AssignComponent implements OnInit {

  ShowColumns: string[] = ['name', 'primarySkill', 'location', 'availability', 'source', 'Action']

  displayedColumns: string[] = ['manager', 'created', 'priority', 'skill', 'status'];
  dataSourceVal: assign[] = [];
  profiles: any;
  Id: string;
  paramsId: any;
  skill: string;
  DetailProfileForm: [];
  dataSource: MatTableDataSource<ProfileData>;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  myModel = false;
  isChecked: any = false;
  checkboxVal: boolean = false;

  @ViewChildren('myCheckbox') myCheckbox: QueryList<MatCheckbox>;
  constructor(
    private route: ActivatedRoute,
    private httpClient: HttpClient,
    private modalService: NgbModal,
    private fb: FormBuilder,
    private assignService: AssignService,
    private router: Router
  ) {
  }

  form: FormGroup = this.fb.group({
    i_agree: ['', [Validators.required]],
  })
  detailForm: FormGroup = this.fb.group({
    id: [''],
    name: [''],
    source: [''],
    location: [''],
    availability: [''],
    primarySkill: [''],
    check: ['']
  })

  ngOnInit() {
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
          this.skill = result.skill;

          this.dataSourceVal.push(result);
          this.dataSourceVal = [...this.dataSourceVal];
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

  getProfile() {
    //console.log(this.skill);
    return this.assignService.getProfileBySkill(this.skill, this.Id).subscribe(
      {
        next: (result: any) => {
          this.profiles = result;
          console.log(result);
          this.dataSource = new MatTableDataSource<ProfileData>(this.profiles);
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;
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

  SubmitProfiles() {

    this.fetchSelectedItems()
  }
  fetchSelectedItems() {

    this.DetailProfileForm = this.profiles.filter((value: { isChecked: any; }, index: any) => {

      return value.isChecked
    });
    this.dataSourceVal[0].profilesList = this.DetailProfileForm;
  }


  viewAssignedProfile(){
    this.assignService.viewAssignedProfile(this.Id).subscribe(
      {
        next: (result: any) => {
          this.router.navigate(['/assignedprofile/' + this.Id]);
          console.log("profiles : " + result);
        },
        error: (err: any) => {
          this.router.navigate(['/assignedprofile']);
          console.log(err);
        },
        complete: () => {
          console.log('complete');
        }
      })
  } 
  GetProfileOnSubmit() {
    this.assignService.getProfileByAssign(this.Id, this.dataSourceVal[0]).subscribe(
      {
        next: (result: any) => {
          this.router.navigate(['/assignedprofile/' + this.Id]);
          console.log("profiles : " + result);
        },
        error: (err: any) => {
          this.router.navigate(['/assignedprofile']);
          console.log(err);
        },
        complete: () => {
          console.log('complete');
        }
      })

  }


  //Detail Start
  openProfile(targetModal: any, profiles: any) {
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'md'
    });
    console.log("Profiles  ", profiles);
    let editedDemandValues = {

      id: profiles.id,
      name: profiles.name,
      source: profiles.source,
      location: profiles.location,

      availability: profiles.availability,
      primarySkill: profiles.primarySkill
      // proposedBy: profiles.proposedBy,

    }
    console.log("editedDemandValues : ", editedDemandValues);
    this.detailForm.patchValue(editedDemandValues);

  }
  //Detail End
}

