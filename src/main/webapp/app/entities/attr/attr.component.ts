import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAttr } from 'app/shared/model/attr.model';
import { AttrService } from './attr.service';
import { AttrDeleteDialogComponent } from './attr-delete-dialog.component';

@Component({
  selector: 'jhi-attr',
  templateUrl: './attr.component.html'
})
export class AttrComponent implements OnInit, OnDestroy {
  attrs?: IAttr[];
  eventSubscriber?: Subscription;

  constructor(protected attrService: AttrService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.attrService.query().subscribe((res: HttpResponse<IAttr[]>) => (this.attrs = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInAttrs();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IAttr): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInAttrs(): void {
    this.eventSubscriber = this.eventManager.subscribe('attrListModification', () => this.loadAll());
  }

  delete(attr: IAttr): void {
    const modalRef = this.modalService.open(AttrDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.attr = attr;
  }
}
