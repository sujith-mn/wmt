import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { map, take } from 'rxjs';
// import { saveAs } from 'file-saver';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { FormBuilder, FormGroup, NgForm, Validators } from '@angular/forms';
import { ProfileService } from '../shared/services/profile.service';
import { Profile } from '../shared/model/profile';

export class ProfileData {

  constructor(
    public id: number,
    public name: string,
    public primarySkill: string,
    public location: string,
    public availability: string,
    public proposedBy: string,
    public source: string
  ) { }
}

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  profiles: ProfileData[];
  closeResult: string;
  private deleteId: any;
  dataSource: MatTableDataSource<ProfileData>;

  skillVal: string[] = ['Java', 'Angular', 'Spring framework', 'React'];
  locationVal: string[] = ['Mumbai', 'Banglore', 'Chennai', 'Hyderabad'];
  availabilityVal: string[] = ['Blocked', 'Available'];
  displayedColumns: string[] = ['id', 'name', 'primarySkill', 'location', 'availability', 'proposedBy', 'source', 'actions']
  profilepath = '';

  fileUploadUrl_Local = 'http://localhost:7001/profiles/excel/upload';

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  detailProfileForm: FormGroup;
  editForm: FormGroup;
  Data: any;

  constructor(
    private _http: HttpClient,
    private modalService: NgbModal,  //Add parameter of type NgbModal
    private profileService: ProfileService,
    private fb: FormBuilder
  ) { }
  newProfile: FormGroup = this.fb.group({
    name: [null, [Validators.required]],
    primarySkill: [null, [Validators.required]],
    location: [null, [Validators.required]],
    availability: [null, [Validators.required]],
    proposedBy: [null, [Validators.required]],
    source: [null, [Validators.required]],
    path: [null, [Validators.required]]
  });
  ngOnInit(): void {
    this.profileService.refreshneeds.subscribe(() => {
      this.getProfile();
    })
    this.getProfile();
    this.editForm = this.fb.group({
      id: [''],
      name: [null, [Validators.required]],
      primarySkill: [null, [Validators.required]],
      location: [null, [Validators.required]],
      availability: [null, [Validators.required]],
      proposedBy: [null, [Validators.required]],
      source: [null, [Validators.required]],
      path: [null, [Validators.required]]
    });
    this.detailProfileForm = this.fb.group({
      id: [''],
      name: [''],
      primarySkill: [''],
      location: [''],
      availability: [''],
      proposedBy: [''],
      source: [''],
      path: ['']
    });
  }

  file: any;
  selectFile(event: any) {
    console.log(event);
    this.file = event.target.files[0];
    console.log(this.file);
  }

  uploadFile() {
    return this.profileService.uploadProfile(this.file).subscribe(
      {
        next: (result: any) => {
          console.log(result);
        },
        error: (err: any) => {
          //this.notificationService.setErrorMsg(err.error)
          console.log(err);
        },
        complete: () => {
          console.log('complete');
        }
      }
    )
  }

  // Upload Resume


  uploadResumeFile() {
    return this.profileService.uploadResume(this.file).subscribe(
      {
        next: (result: any) => {
          this.addProfile();
          console.log(result);
        },
        error: (err: any) => {
          //this.notificationService.setErrorMsg(err.error)
          console.log(err);
        },
        complete: () => {
          console.log('complete');
        }
      }
    )
  }
  getProfile() {
    return this.profileService.getAllProfiles().subscribe(
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


  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();

    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }


  //  open start
  open(content: any) {
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title' }).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }
  // open end


  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  addProfile() {

    var a = this.newProfile.value;
    console.log(a);

    return this.profileService.NewProfile(a).subscribe(
      {
        next: (result: any) => {
          console.log(result);
          // this.uploadResumeFile();
        },
        error: (err: any) => {
          console.log(err);
        },
        complete: () => {
          console.log('complete');
          this.newProfile.reset();

        }
      }

    )

  }

  onSubmitProfile() {
    this.uploadResumeFile();


  }
  onSubmit(f: NgForm) {
    // this.modalService.dismissAll(); //dismiss the modal
  }


  //Profile Detail Start
  openProfileDetails(targetModal: any, profile: Profile) {
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'md'
    });
    let editedProfileValues = {
      id: profile.id,
      name: profile.name,
      primarySkill: profile.primarySkill,
      location: profile.location,
      availability: profile.availability,
      proposedBy: profile.proposedBy,
      source: profile.source,
      path: profile.path
    }
    this.detailProfileForm.patchValue(editedProfileValues);
  }
  //Profile Detail End


  //Edit start
  openProfileEdit(targetModal: any, profile: Profile) {
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'md'
    });

    let editedProfileValues = {
      id: profile.id,
      name: profile.name,
      primarySkill: profile.primarySkill,
      location: profile.location,
      availability: profile.availability,
      proposedBy: profile.proposedBy,
      source: profile.source,
      path: profile.path
    }

    this.profilepath = profile.path
    this.editForm.patchValue(editedProfileValues);
  }
  // Edit End .
  //Save Edit start


  editedProfile() {
    return this.profileService.editProfile(this.editForm.value.id, this.editForm.value).subscribe(
      {
        next: (result: any) => {
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

  uploadEditedResumeFile() {
    return this.profileService.uploadResume(this.file).subscribe(
      {
        next: (result: any) => {
          this.editedProfile();
          console.log(result);
        },
        error: (err: any) => {
          //this.notificationService.setErrorMsg(err.error)
          console.log(err);
        },
        complete: () => {
          console.log('complete');
        }
      }
    )
  }

  onSave() {
    this.uploadEditedResumeFile();


  }
  //Save Edit End

  // openDelete start
  openDelete(targetModal: any, profile: Profile) {
    this.deleteId = profile.id;
    console.log(profile.id);
    this.modalService.open(targetModal, {
      backdrop: 'static',
      size: 'md'
    });
  }
  //openDelete End

  //On delete start
  onDelete() {


    return this.profileService.deleteProfile(this.deleteId).subscribe(

      {

        next: (result: any) => {

          this.modalService.dismissAll();
        },
        error: (err: any) => {
          this.modalService.dismissAll();
        },
        complete: () => {
          console.log('complete');
        }

      });
  }//On delete END

  //Resume Download Start
  DownloadFile(Data: any) {
    console.log(Data.id);
    return this.profileService.GetResume(Data.id).pipe(take(1))
      .subscribe((response: any) => {
        console.log(response)
        const downloadLink = document.createElement('a');
        // downloadLink.href = URL.createObjectURL(new Blob([response.body], { type: response.body.type }));
        downloadLink.href = response.url;
        console.log(downloadLink.href)
        // const contentDisposition = response.headers.get('content-disposition');
        // console.log(contentDisposition)
        // const fileName = contentDisposition.split(';')[1].split('filename')[1].split('=')[1].trim();
        downloadLink.download = "Resume";
        downloadLink.click();
      });

  }

  //Resume Download END
}


