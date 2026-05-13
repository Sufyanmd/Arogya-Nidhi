package com.example.arogyanidhi.ui.schemes

import androidx.lifecycle.ViewModel
import com.example.arogyanidhi.domain.model.Scheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SchemeViewModel @Inject constructor() : ViewModel() {
    private val _schemes = MutableStateFlow<List<Scheme>>(listOf(
        Scheme(
            id = "1",
            name = "Ayushman Bharat (PM-JAY)",
            description = "The world's largest health insurance scheme, providing ₹5 lakh coverage per family annually.",
            eligibilityCriteria = "SECC 2011 database listed families, low-income households, and specific occupational categories.",
            benefits = "Cashless access to health services at the point of service in any empaneled public or private hospital.",
            category = "Health"
        ),
        Scheme(
            id = "2",
            name = "PM Kisan Samman Nidhi",
            description = "Income support for all landholding farmer families in the country.",
            eligibilityCriteria = "All small and marginal farmer families (subject to exclusion criteria).",
            benefits = "Direct financial support of ₹6,000 per year in three equal installments.",
            category = "Agriculture"
        ),
        Scheme(
            id = "3",
            name = "Pradhan Mantri Awas Yojana (PMAY)",
            description = "A flagship mission by the Government of India providing affordable housing for the urban and rural poor.",
            eligibilityCriteria = "Low Income Group (LIG), Economically Weaker Section (EWS), and Middle Income Group (MIG).",
            benefits = "Credit Linked Subsidy Scheme (CLSS) or direct financial assistance for house construction.",
            category = "Housing"
        ),
        Scheme(
            id = "4",
            name = "Janani Suraksha Yojana (JSY)",
            description = "Safe motherhood intervention under the National Rural Health Mission.",
            eligibilityCriteria = "All pregnant women delivering in government health centres.",
            benefits = "Cash assistance for institutional delivery to reduce maternal and neonatal mortality.",
            category = "Health"
        ),
        Scheme(
            id = "5",
            name = "PM Mudra Yojana (PMMY)",
            description = "Loans provided to non-corporate, non-farm small/micro enterprises.",
            eligibilityCriteria = "Small business owners, shopkeepers, fruit/vegetable vendors, and small industries.",
            benefits = "Shishu (up to ₹50k), Kishore (₹50k-₹5L), and Tarun (₹5L-₹10L) loan categories.",
            category = "Employment"
        ),
        Scheme(
            id = "6",
            name = "Atal Pension Yojana (APY)",
            description = "Pension scheme focused on the unorganized sector workers.",
            eligibilityCriteria = "Citizens of India between 18 and 40 years of age.",
            benefits = "Fixed minimum pension ranging from ₹1,000 to ₹5,000 per month after age 60.",
            category = "Social Security"
        ),
        Scheme(
            id = "7",
            name = "PM Jeevan Jyoti Bima Yojana",
            description = "A one-year life insurance scheme, renewable from year to year.",
            eligibilityCriteria = "Individuals in the age group of 18 to 50 years having a bank account.",
            benefits = "Life cover of ₹2 lakh in case of death for any reason.",
            category = "Insurance"
        ),
        Scheme(
            id = "8",
            name = "PM Suraksha Bima Yojana",
            description = "An accidental insurance scheme providing coverage for death or disability.",
            eligibilityCriteria = "People in the age group of 18 to 70 years with a bank account.",
            benefits = "Risk coverage of ₹2 lakh for accidental death and full disability.",
            category = "Insurance"
        ),
        Scheme(
            id = "9",
            name = "Sukanya Samriddhi Yojana",
            description = "A small deposit scheme for the girl child as part of 'Beti Bachao Beti Padhao' campaign.",
            eligibilityCriteria = "Parents/Guardians of a girl child aged below 10 years.",
            benefits = "High interest rate and tax benefits for the girl's education and marriage.",
            category = "Social Welfare"
        ),
        Scheme(
            id = "10",
            name = "Pradhan Mantri Jan Dhan Yojana",
            description = "Financial inclusion program ensuring access to financial services like banking, savings, and insurance.",
            eligibilityCriteria = "Any individual without a bank account.",
            benefits = "Zero balance account, RuPay debit card, and accidental insurance cover of ₹2 lakh.",
            category = "Banking"
        )
    ))
    val schemes: StateFlow<List<Scheme>> = _schemes.asStateFlow()
}
