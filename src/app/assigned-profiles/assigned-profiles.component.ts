import { HttpClient } from '@angular/common/http';
import { Component, ViewChild } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { ProfileData } from '../profile/profile.component';
import { AssignProfileService } from '../shared/services/assigned-profiles';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
@Component({
  selector: 'app-assigned-profiles',
  templateUrl: './assigned-profiles.component.html',
  styleUrls: ['./assigned-profiles.component.css']
})
export class AssignedProfilesComponent {
  constructor(
    private httpClient: HttpClient,
    private fb: FormBuilder,
    private assignProfileService:AssignProfileService,
    private router:Router,
    private route: ActivatedRoute
  ) {
  }
  public id: string;
  profiles: ProfileData[];
  dataSource: MatTableDataSource<ProfileData>;
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  displayedColumns: string[] = ['id', 'name', 'primarySkill', 'location', 'availability', 'proposedBy', 'source','actions']
  ngOnInit(): void {
    this.id = this.route.snapshot.paramMap.get('id');
    this.getProfile()
  }
  getProfile() {
    return this.assignProfileService.getAssignedProfileLists(this.id).subscribe(
      {
        next: (result: any) => {
          console.log(result);
          this.profiles = result;
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

  status(value,id){
    var status = value;
    if(status == 'accepted'){
      status = [1];
    }
    else{
      status = [0]
    }
    var profileidValue  = this.profiles.find(test => test.id === id);
    profileidValue['demandRejectedStatus'] = status;
    profileidValue['profileStatus'] = value;
    console.log(profileidValue);

    this.profiles
    return this.assignProfileService.statusValidation(profileidValue,this.id).subscribe({
      next: (result: any) => {
        console.log(result);
      },
      error: (err: any) => {
        console.log(err);
      },
      complete: () => {
        console.log('complete');
      }
    });
  }
}
