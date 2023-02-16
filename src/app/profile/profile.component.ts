import { HttpClient } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { map } from 'rxjs';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { NgForm } from '@angular/forms';
export class ProfileData{

  constructor(
    public id: number,
    public name: string,
    public primarySkill: string,
    public location: string,
    public  availability: string,
    public  proposedBy: string,
    public  source: string
  ){}
}

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit{

  profiles: ProfileData[];
  closeResult: string; 
  dataSource: MatTableDataSource<ProfileData>; 
  
	displayedColumns: string[] = ['id', 'name', 'primarySkill', 'location', 'availability', 'proposedBy', 'source']


  fileUploadUrl_Local='http://localhost:7001/profiles/excel/upload';
  
  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(
    private _http:HttpClient,
    private modalService: NgbModal  //Add parameter of type NgbModal
  ){}
  ngOnInit(): void {
    this.getProfile() ;
  }

  file:any;
  selectFile(event: any){
    console.log(event);
    this.file = event.target.files[0];
    console.log(this.file);
  }

  uploadFile(){
    let formData = new FormData();
    formData.append("file",this.file);

    this._http.post('http://localhost:7001/profiles/excel/upload',formData)
    .subscribe(
      (data:any)=>{
        //success 
        alert(data.message);
      },
      (error)=>{
        //error
        alert(error.message);
      }
      );
    
  }

  
	getProfile() {
		this._http.get<any>('http://localhost:7001/profiles').subscribe(
      response => {
        console.log(response);
        this.profiles = response;
        this.dataSource=new MatTableDataSource<ProfileData>(this.profiles);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
      }
    );
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

  onSubmit(f: NgForm) {
    this.modalService.dismissAll(); //dismiss the modal
  }
}
