package com.usa.his.gov.dc.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import static com.usa.his.gov.dc.constant.HisDataCollectionConstant.*;
import com.usa.his.gov.dc.model.HisCaseDtls;
import com.usa.his.gov.dc.model.HisCasePlan;
import com.usa.his.gov.dc.model.HisCrimeDtls;
import com.usa.his.gov.dc.model.HisFamilyDtls;
import com.usa.his.gov.dc.model.HisJobDtls;
import com.usa.his.gov.dc.model.HisKidsDtls;
import com.usa.his.gov.dc.service.HisCaseDtlservice;
import com.usa.his.gov.exception.HisException;
import com.usa.his.gov.plan.model.HisPlan;
import com.usa.his.gov.plan.service.HisPlanService;

@Controller
@RequestMapping("/DC")
public class HISDCController {
	/**
	 * Enable logging for the class
	 */
	private static final Logger log = LoggerFactory.getLogger(HISDCController.class);
	/**
	 * inject HisCaseDtlservice create an object
	 */
	@Autowired
	private HisCaseDtlservice caseService;
	@Autowired
	private HisPlanService planService;
	private String backToo;

	@GetMapping("/showCaseForm")
	public String caseForm(Model model) {
		log.info("HISDCController caseForm() method starting");
		model.addAttribute(CASE_DETAILS, new HisCaseDtls());
		log.info("HISDCController caseForm() method end");
		return CREATE_CASE_FORM_URL;
	}

	@PostMapping("/processCaseForm")
	public String processCaseForm(HisCaseDtls caseDtls, Model model)
			throws HisException {
		log.info("HISDCController caseForm() method starting");
		caseDtls.setUserId(503);
		HisCaseDtls returnValue = caseService.newCase(caseDtls);
		if (returnValue == null) {
			log.info("HISDCController caseForm() method end with exception");
			throw new HisException();
		}
		log.info("HISDCController caseForm() method end success");
		model.addAttribute(CASE_DETAILS, returnValue);
		return REDIRECT_TO_SELECT_PLAN+"?caseNumber="+returnValue.getCaseNumber();
	}

	@GetMapping("/showSelectPlan")
	public String selectPlan(@RequestParam("caseNumber") Integer caseNumber,Model model) throws HisException {
		log.info("HISDCController selectPlan() method starting");
		HisCasePlan casePlan = caseService.getPlanByCaseNumber(caseNumber);
		if (casePlan==null) {
			List<HisPlan> plans = planService.getAllPlans();
			model.addAttribute(CASE_NUMBER,caseNumber);
			model.addAttribute(CASE_PALN_DETAILS, new HisCasePlan());
			model.addAttribute(ALL_PLANS, plans);
			log.info("HISDCController selectPlan() method success");
			return SELECT_PALN_FORM_URL;
		}else {
			return REDIRECT_TO_FUMILY+caseNumber;
		}

	}

	@PostMapping("/processSelectPlanForm")
	public String selectPlanProcess(HisCasePlan casePlan, Model model) throws HisException {
		log.info("HISDCController selectPlanProcess() method starting");
		HisCasePlan returnValue = caseService.addCasePlan(casePlan);
		if (returnValue != null) {
			log.info("HISDCController selectPlanProcess() method end success");
			return REDIRECT_TO_FUMILY+casePlan.getCaseNumber();
		} else {
			log.info("HISDCController selectPlanProcess() method end with exception");
			throw new HisException();
		}

	}

	@GetMapping("/showFamilyForm")
	public String showFamilyForm(@RequestParam("caseNumber") Integer caseNumber,Model model) throws HisException {
		log.info("HISDCController showFamilyForm() method starting");
		HisFamilyDtls familyDtls = caseService.getFamilyByCase(caseNumber);
		model.addAttribute(CASE_NUMBER, caseNumber);
		model.addAttribute(PROCESS_HANDELER, "processFamily");
		if (familyDtls==null) {
			model.addAttribute(FAMILY_DETAILS, new HisFamilyDtls());
			log.info("HISDCController showFamilyForm() method end");
			return FAMILY_FORM;
		}else {
			model.addAttribute(FAMILY_DETAILS, familyDtls);
			return FAMILY_FORM;
		}

	}

	@PostMapping("/processFamily")
	public String processFamilyForm(HisFamilyDtls familyDtls, Model model) throws HisException {
		log.info("HISDCController processFamilyForm() method starting");
		backToo=BACK_TO_FAMILY;
		HisFamilyDtls returnValue = caseService.addFamilyDtls(familyDtls);
		log.info("ghdgh" + returnValue.toString());
		System.out.println(returnValue.toString());
		HisCaseDtls caseDetails = caseService.getCaseDetails(returnValue.getCaseNumber());
		if (returnValue.getHaveChild()) {
			log.info("HISDCController processFamilyForm() method end go to kids");
			return REDIRECT_TO_KIDS_DETAILS+returnValue.getCaseNumber();
		} else {
			return REDIRECT_TO_JOB_DETAILS+ caseDetails.getCaseNumber()+backToo;

		}
	}


	@GetMapping("/showKidsForm")
	public String showKidsForm(@RequestParam("caseNumber") Integer caseNumber,Model model) throws HisException {
		List<HisKidsDtls> allKids = caseService.getAllKids(caseNumber);
		if (allKids != null) {
			model.addAttribute(KIDS_DETAILS_LIST, allKids);
		}
		backToo=BACK_TO_KIDS;
		model.addAttribute(BACK_TO, backToo);
		model.addAttribute(KIDS_DETAILS, new HisKidsDtls());
		model.addAttribute(CASE_NUMBER, caseNumber);
		model.addAttribute(PROCESS_HANDELER, "processKidsForm");
		return KIDS_FORM;
	}

	@PostMapping("/processKidsForm")
	public String processKidsForm(HisKidsDtls kidsDtls, Model model) throws HisException {
		HisKidsDtls returnValue = caseService.addKid(kidsDtls);
		model.addAttribute(PROCESS_HANDELER, "processKidsForm");
		if (returnValue != null) {
			model.addAttribute(KIDS_DETAILS, new HisKidsDtls());
			List<HisKidsDtls> allKids = caseService.getAllKids(returnValue.getCaseNumber());
			HisCaseDtls caseDetails = caseService.getCaseDetails(returnValue.getCaseNumber());
			model.addAttribute(CASE_DETAILS, caseDetails);
			model.addAttribute(KIDS_DETAILS_LIST, allKids);
			return REDIRECT_TO_KIDS_DETAILS+returnValue.getCaseNumber();
		} else {
			return KIDS_FORM;
		}
	}
	@GetMapping("/deleteKids")
	public String deleteKidsProcess(
			@RequestParam("childId") Integer childId,
			@RequestParam("from")String from,
			@RequestParam("caseNumber")Integer caseNumber,
			Model model) throws HisException {
		log.info("delete Start");
		caseService.deleteKids(childId);
		model.addAttribute(KIDS_DETAILS, new HisKidsDtls());
		List<HisKidsDtls> allKids = caseService.getAllKids(caseNumber);
		model.addAttribute(CASE_NUMBER, caseNumber);
		model.addAttribute(KIDS_DETAILS_LIST, allKids);
		if (from.equalsIgnoreCase("processEditKids")) {
			log.info("in if Start");
			model.addAttribute(PROCESS_HANDELER, "processEditKids");
			return "redirect:/Dc/showEditKids?caseNumber="+caseNumber;
		}else {
			model.addAttribute(PROCESS_HANDELER, "processKidsForm");
			log.info("out  if Start");
			return REDIRECT_TO_KIDS_DETAILS+caseNumber;
		}
		
	}
	@GetMapping("/updateKids")
	public String updateKids(
			@RequestParam("childId") Integer childId,
			@RequestParam("from")String from,
			@RequestParam("caseNumber")Integer caseNumber,Model model) throws HisException {
		
		backToo=BACK_TO_KIDS;
		model.addAttribute(BACK_TO, backToo);
		HisKidsDtls kidDtls = caseService.getKidById(childId);
		List<HisKidsDtls> allKids = caseService.getAllKids(caseNumber);
		model.addAttribute(CASE_NUMBER, caseNumber);
		model.addAttribute(KIDS_DETAILS_LIST, allKids);
		model.addAttribute(KIDS_DETAILS, kidDtls);
		if (from.equalsIgnoreCase("processEditKids")) {
			model.addAttribute(PROCESS_HANDELER, "processEditKids");
			model.addAttribute(KIDS_DETAILS, kidDtls);
			return KIDS_FORM;
//			return "redirect:/DC/showEditKids?caseNumber="+caseNumber;
		}else {
			model.addAttribute(PROCESS_HANDELER, "processKidsForm");
			return KIDS_FORM;
		}
		
	}



	@GetMapping("/showJobForm")
	public String showJobForm(@RequestParam("caseNumber") Integer caseNumber,
			@RequestParam("backTo") String backTo,
			Model model) throws HisException {
		HisJobDtls hisJobDtls = caseService.getJobByCaseNumber(caseNumber);
		model.addAttribute(CASE_NUMBER, caseNumber);
		model.addAttribute(PROCESS_HANDELER, "processJobForm");
		model.addAttribute(BACK_TO, backTo);
		if (hisJobDtls==null) {
			model.addAttribute(JOB_DETAILS, new HisJobDtls());
			return JOB_FORM;	
		}else {
			model.addAttribute(JOB_DETAILS, hisJobDtls);
			return JOB_FORM;
		}
	}

	@PostMapping("/processJobForm")
	public String processJobForm(HisJobDtls jobDtls, Model attributes) throws HisException {
		HisJobDtls returnValue = caseService.addJobDetails(jobDtls);
		if (returnValue != null) {
			HisCaseDtls caseDetails = caseService.getCaseDetails(returnValue.getCaseNumber());
			attributes.addAttribute(CASE_DETAILS, caseDetails);
			return REDIRECT_TO_CRIME+caseDetails.getCaseNumber();
		} else {
			attributes.addAttribute(MESSAGE, "you can adding this info");
			return JOB_FORM;
		}
	}

	@GetMapping("/showCrimeForm")
	public String showCrimeForm(@RequestParam("caseNumber") Integer caserNumber, Model model) throws HisException {
		HisCrimeDtls crimeDtls = caseService.getCrimeDtls(caserNumber);
		model.addAttribute(CASE_NUMBER, caserNumber);
		model.addAttribute(PROCESS_HANDELER, "processCrimeForm");
		model.addAttribute(BACK_TO,backToo);
		if (crimeDtls==null) {
		model.addAttribute(CRIME_DETAILS, new HisCrimeDtls());
		return CRIME_FORM;
		}else {
			model.addAttribute(CRIME_DETAILS, crimeDtls);
			return CRIME_FORM;
		}
	}

	@PostMapping("/processCrimeForm")
	public String processCrimeForm(HisCrimeDtls crimeDtls, Model attributes) throws HisException {
		log.info("in HISDCController processCrimeForm() method starting");
		HisCrimeDtls returnValue = caseService.addCrimeDetails(crimeDtls);
		if (returnValue != null) {
			log.info("in HISDCController processCrimeForm() method save");
			attributes.addAttribute(MESSAGE, "Data Collection Successfuly");
			return REDIRECT_CASE_CREATED_PAGE+returnValue.getCaseNumber();
		} else {
			log.info("in HISDCController processCrimeForm() method not save");
			return CRIME_FORM;
		}
	}

	@GetMapping("/showCaseCreated")
	public String CaseCreated(@RequestParam("caseNumber") Integer caseNumber, Model model) throws HisException {
		HisCaseDtls caseDetails = caseService.getCaseDetails(caseNumber);
		HisFamilyDtls familyDtls = caseService.getFamilyByCase(caseNumber);
		HisJobDtls jobDtls = caseService.getJobByCaseNumber(caseNumber);
		HisCrimeDtls crimeDtls = caseService.getCrimeDtls(caseNumber);
		
		model.addAttribute(CASE_DETAILS, caseDetails);
		model.addAttribute(FAMILY_DETAILS, familyDtls);
		model.addAttribute(JOB_DETAILS, jobDtls);
		model.addAttribute(CRIME_DETAILS, crimeDtls);
		if (familyDtls.getHaveChild()) {
			List<HisKidsDtls> allKids = caseService.getAllKids(caseNumber);
			model.addAttribute(KIDS_DETAILS_LIST, allKids);
		}

		return CASE_CREATED;
	}

	@GetMapping("/showEditFamily")
	public String showEditFamly(@RequestParam("caseNumber") Integer caseNumber,Model model) throws HisException {
		log.info("HISDCController showEditFamly() method starting");
		HisFamilyDtls familyDtls = caseService.getFamilyByCase(caseNumber);
		model.addAttribute(PROCESS_HANDELER, "processEditFamily");
		model.addAttribute(FAMILY_DETAILS,familyDtls);
		log.info("HISDCController showEditFamly() method end");
		return FAMILY_FORM;
	}
	@PostMapping("/processEditFamily")
	public String processEditFamly(HisFamilyDtls familyDtls, Model attributes) throws HisException {
		log.info("HISDCController processFamilyForm() method starting");
		HisFamilyDtls returnValue = caseService.addFamilyDtls(familyDtls);
		HisCaseDtls caseDetails = caseService.getCaseDetails(returnValue.getCaseNumber());
		attributes.addAttribute(CASE_DETAILS, caseDetails);
		log.info("HISDCController processFamilyForm() method end go to kids");
		return REDIRECT_CASE_CREATED_PAGE+returnValue.getCaseNumber();
	}
	@GetMapping("/showEditjobForm")
	public String showEditJob(@RequestParam("caseNumber") Integer caseNumber, Model model) throws HisException {
		HisJobDtls jobDtls = caseService.getJobByCaseNumber(caseNumber);
		model.addAttribute(PROCESS_HANDELER, "processEditJob");
		model.addAttribute(CASE_NUMBER, caseNumber);
		model.addAttribute(JOB_DETAILS, jobDtls);
		return JOB_FORM;
	}

	@PostMapping("/processEditJob")
	public String processEditJob(HisJobDtls jobDtls, Model attributes) throws HisException {
		HisJobDtls returnValue = caseService.addJobDetails(jobDtls);
		if (returnValue != null) {
			HisCaseDtls caseDetails = caseService.getCaseDetails(returnValue.getCaseNumber());
			attributes.addAttribute(CASE_DETAILS, caseDetails);
			return REDIRECT_CASE_CREATED_PAGE+caseDetails.getCaseNumber();
		} else {
			attributes.addAttribute(MESSAGE, "you can adding this info");
			return JOB_FORM;
		}

	}
	@GetMapping("/showEditCrime")
	public String showEditCrime(@RequestParam("caseNumber") Integer caserNumber, Model model) throws HisException {
		HisCrimeDtls crimeDtls = caseService.getCrimeDtls(caserNumber);
		model.addAttribute(CASE_NUMBER, caserNumber);
		model.addAttribute(CRIME_DETAILS, crimeDtls);
		model.addAttribute(PROCESS_HANDELER, "processEditCrime");
		return CRIME_FORM;
	}
	@PostMapping("/processEditCrime")
	public String processEditCrime(HisCrimeDtls crimeDtls, Model attributes) throws HisException {
		log.info("in HISDCController processCrimeForm() method starting");
		HisCrimeDtls returnValue = caseService.addCrimeDetails(crimeDtls);
		if (returnValue != null) {
			log.info("in HISDCController processCrimeForm() method save");
			attributes.addAttribute(MESSAGE, "Data Collection Successfuly");
			return REDIRECT_CASE_CREATED_PAGE+returnValue.getCaseNumber();
		} else {
			log.info("in HISDCController processCrimeForm() method not save");
			return CRIME_FORM;
		}
	}
	@GetMapping("/showEditKids")
	public String showEditKids(@RequestParam("caseNumber")Integer caseNumber, Model model) throws HisException {
		model.addAttribute(KIDS_DETAILS, new HisKidsDtls());
		model.addAttribute(PROCESS_HANDELER, "processEditKids");
			List<HisKidsDtls> allKids = caseService.getAllKids(caseNumber);
			model.addAttribute(CASE_NUMBER, caseNumber);
			model.addAttribute(KIDS_DETAILS_LIST, allKids);
			return KIDS_FORM;
	}
	@PostMapping("/processEditKids")
	public String processEditKids(HisKidsDtls kidsDtls, Model model) throws HisException {
		HisKidsDtls returnValue = caseService.addKid(kidsDtls);
		model.addAttribute(KIDS_DETAILS, new HisKidsDtls());
		model.addAttribute(PROCESS_HANDELER, "processEditKids");
		if (returnValue != null) {
			List<HisKidsDtls> allKids = caseService.getAllKids(returnValue.getCaseNumber());
			HisCaseDtls caseDetails = caseService.getCaseDetails(returnValue.getCaseNumber());
			model.addAttribute(CASE_DETAILS, caseDetails);
			model.addAttribute(KIDS_DETAILS_LIST, allKids);
			return KIDS_FORM;
		} else {
			return KIDS_FORM;
		}
		
	}
}
