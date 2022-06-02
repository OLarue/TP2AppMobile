package ca.qc.cstj.tp2.presentation.ui.gateways.detail

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import ca.qc.cstj.tp2.R
import ca.qc.cstj.tp2.core.ColorHelper
import ca.qc.cstj.tp2.core.Constants
import ca.qc.cstj.tp2.core.Resource
import ca.qc.cstj.tp2.databinding.FragmentDetailGatewayBinding
import ca.qc.cstj.tp2.domain.models.Gateway
import com.bumptech.glide.Glide
import ca.qc.cstj.tp2.core.loadFromResource

class DetailGatewayFragment : Fragment(R.layout.fragment_detail_gateway){
    private val binding: FragmentDetailGatewayBinding by viewBinding()
    private val viewModel: DetailGatewayViewModel by viewModels{
        DetailGatewayViewModel.Factory(args.href)
    }

    private val args : DetailGatewayFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.gateway.observe(viewLifecycleOwner){
            when(it){
                is Resource.Error -> {
                    Toast.makeText(requireContext(), it.throwable.message, Toast.LENGTH_LONG).show()
                    //pour revenir au fragment d'avant
                    requireActivity().supportFragmentManager.popBackStack()
                }
                is Resource.Success -> {
                    val gateway = it.data!!

                    showGatewayInfo(gateway)
                }
            }
        }

        binding.btnReboot.setOnClickListener {
            viewModel.reboot()
        }

        binding.btnUpdate.setOnClickListener {

        }
    }

    private fun showGatewayInfo(gateway: Gateway){

        if (gateway.connection.status == "Online"){
            with(binding){
                grpInfoGateway.visibility = View.VISIBLE
                txvOffline.visibility = View.INVISIBLE
                txvIPAddress.text = gateway.connection.ip
                txvPing.text = "${gateway.connection.ping} ns"
                txvDownload.text = "${gateway.connection.download} Ebps"
                txvUpload.text = "${gateway.connection.upload} Ebps"
                txvSignalQuality.text = "${gateway.connection.signal} dBm"

            }
        } else {
            with(binding){
                grpInfoGateway.visibility = View.INVISIBLE
                txvOffline.visibility = View.VISIBLE


            }
        }
        with(binding){
            chpStatusGateway.chipBackgroundColor = ColorHelper.connectionStatusColor(root.context, gateway.connection.status)
            chpStatusGateway.text = gateway.connection.status
            txvSerialNumber.text = gateway.serialNumber
            txvMacAddress.text = gateway.config.mac
            txvSSID.text = "SSID: ${gateway.config.SSID}"
            txvPIN.text = "PIN: ${gateway.pin}"
            txvHash.text = gateway.hash
            txvKernelRevisionVersion.text = "Kernel Revision: ${gateway.config.kernelRevision} Version: ${gateway.config.version}"

            imvKernel1.loadFromResource(requireContext(), "element_"+ gateway.config.kernel[0].lowercase())
            imvKernel2.loadFromResource(requireContext(), "element_"+ gateway.config.kernel[1].lowercase())
            imvKernel3.loadFromResource(requireContext(), "element_"+ gateway.config.kernel[2].lowercase())
            imvKernel4.loadFromResource(requireContext(), "element_"+ gateway.config.kernel[3].lowercase())
            imvKernel5.loadFromResource(requireContext(), "element_"+ gateway.config.kernel[4].lowercase())

        }
    }
}