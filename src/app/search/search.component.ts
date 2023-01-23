import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort} from '@angular/material/sort';
import {MatTableDataSource} from '@angular/material/table';
import { Demand } from '../demand/demand.component';
import { FormBuilder, FormGroup, NgForm } from '@angular/forms';

import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { __values } from 'tslib';
export interface demandData {
  id: string;
  manager: string;
  created: string;
  endDate : string;
  ageing: string;
  priority: string;
  skill: string;
  status: string;
}
@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit{
 demands: demandData[];
 editForm: FormGroup;
//  private fb: FormBuilder;
 private deleteId: number;
 closeResult: string; 
 displayedColumns:string[] = ['id','manager','created','endDate','ageing','priority','skill','status','actions'];
 dataSource: MatTableDataSource<demandData>;  

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;
  
  
  constructor(private httpClient: HttpClient,
    private modalService: NgbModal,  //Add parameter of type NgbModal
    private fb: FormBuilder        //Add parameter of type FormBuilder.
    ) {

    // Assign the data to the data source for the table to render
   // this.dataSource = new MatTableDataSource<Demand>(this.demands);
  }
  ngOnInit():void {
    
    this.getDemands();
    this.editForm = this.fb.group({
      id: [''],
      manager: [''],
      created: [''],
      endDate:[''],
      ageing: [''],
      priority: [''],
      skill: [''],
      status: ['']
    });
  }
  
  getDemands() {
    this.httpClient.get<any>('http://localhost:7000/api/demands/').subscribe(
      response => {
        
        this.demands = response;
        this.dataSource=new MatTableDataSource<demandData>(this.demands);
        this.dataSource.paginator = this.paginator;
        this.dataSource.sort = this.sort;
        console.log("response -->",response);
        console.log("DataSource -->",this.dataSource);
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

    const headerOptions = new HttpHeaders();
    headerOptions.set('Content-Type', 'application/json');
    const url = 'http://localhost:7000/api/demands/';
    this.httpClient.post(url, f.value, { headers: headerOptions })
      .subscribe((result) => {
        console.log(result);
        this.ngOnInit(); //reload the tablex`
      });
    this.modalService.dismissAll(); //dismiss the modal
  }
  
  //Detail Start
  openDetails(targetModal: any, demand: Demand) {
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'lg'
    });
    document.getElementById('manager_Detail')?.setAttribute('value', demand.manager);
    document.getElementById('created_Detail')?.setAttribute('value', demand.created);
    document.getElementById('end_date_Detail')?.setAttribute('value', demand.endDate);
    document.getElementById('ageing_Detail')?.setAttribute('value', demand.ageing);
    document.getElementById('priority_Detail')?.setAttribute('value', demand.priority);
    document.getElementById('skill_Detail')?.setAttribute('value', demand.skill);
    document.getElementById('status_Detail')?.setAttribute('value', demand.status);

  }
  //Detail End 

  //Edit start
  openEdit(targetModal: any, demand: Demand) {
    this.modalService.open(targetModal, {
      centered: true,
      backdrop: 'static',
      size: 'lg'
    });
    this.editForm.patchValue({
      id: demand.id,
      manager: demand.manager,
      created: demand.created,
      endDate:demand.endDate,
      ageing: demand.ageing,
      priority: demand.priority,
      skill: demand.skill,
      status: demand.status

    });
  }
  // Edit End .

  //Save Edit start
  onSave() {
    const editURL = 'http://localhost:7000/api/demands/' + this.editForm.value.id;
    console.log("onSave-->",this.editForm.value);
    this.httpClient.put(editURL, this.editForm.value)
      .subscribe((results) => {
        this.ngOnInit();
        this.modalService.dismissAll();
      });
  }
  //Save Edit End

   // openDelete start
   openDelete(targetModal: any, demand: Demand) {
    this.deleteId = demand.id;
    this.modalService.open(targetModal, {
      backdrop: 'static',
      size: 'lg'
    });
  }
  //openDelete End

  //On delete start
  onDelete() {
    const deleteURL = 'http://localhost:7000/api/demands/' + this.deleteId;
    this.httpClient.delete(deleteURL)
      .subscribe((results) => {
        this.ngOnInit();
        this.modalService.dismissAll();
      });
  }
}

