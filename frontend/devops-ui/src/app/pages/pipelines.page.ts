import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { PipelineResponse, PipelineRunResponse, PipelineService } from '../core/services/pipeline.service';

@Component({
  selector: 'app-pipelines-page',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './pipelines.page.html',
  styleUrl: './pipelines.page.scss'
})
export class PipelinesPage implements OnInit {
  pipelines: PipelineResponse[] = [];
  runs: PipelineRunResponse[] = [];
  isLoading = false;
  errorMessage = '';

  constructor(private pipelineService: PipelineService) {}

  ngOnInit(): void {
    this.loadData();
  }

  loadData(): void {
    this.isLoading = true;
    this.pipelineService.getPipelines().subscribe({
      next: (data) => {
        this.pipelines = data;
        this.isLoading = false;
      },
      error: () => {
        this.errorMessage = 'Could not load pipelines.';
        this.isLoading = false;
      }
    });

    this.pipelineService.getRuns().subscribe({
      next: (data) => this.runs = data,
      error: () => {}
    });
  }

  runPipeline(id: string): void {
    this.pipelineService.runPipeline(id, {
      branch: 'main',
      commitSha: 'frontend-run-' + Date.now()
    }).subscribe({
      next: () => this.loadData(),
      error: () => this.errorMessage = 'Could not run pipeline.'
    });
  }
}
